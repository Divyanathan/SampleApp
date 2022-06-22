package com.example.showlist.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.showlist.BuildConfig
import com.example.showlist.adapter.CustomAdapter
import com.example.showlist.constants.Constants
import com.example.showlist.databinding.ActivityMainBinding
import com.example.showlist.viewmodel.CustomViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: CustomViewModel by viewModels()
    private lateinit var mCustomAdapter : CustomAdapter
    val REQ_CODE_PERMISSION_CAMERA = 1001
    val REQUEST_IMAGE_CAPTURE = 1002
    var mCurrentPhotoPath = ""
    var mPhotoPosition = 0
    private val TAG ="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initCustomAdapter()
        setObserver()
        mViewModel.getItemList()
        mBinding.backArrowIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setObserver() {
        mViewModel.mItemList.observe(this){
            mCustomAdapter.submitList(it)
            Log.d(TAG, "setObserver: "+ it.size)
        }

        mViewModel.mOnItemChanged.observe(this){
            mCustomAdapter.notifyItemChanged(it)
        }
    }

    private fun initCustomAdapter(){

        var lLayoutManager = LinearLayoutManager(this)
        mBinding.recylerView.layoutManager = lLayoutManager

        mCustomAdapter = CustomAdapter(object :CustomAdapter.CustomListener{
            override fun onPhotoClicked(pPosition: Int) {
                if ( ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPosition = pPosition
                    triggerCameraIntent()
                }else{
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.CAMERA),
                        REQ_CODE_PERMISSION_CAMERA
                    )
                }
            }

            override fun onOpenImagePage(pPhotoUrl: String) {
                val lIntent = Intent(this@MainActivity,ImageViewActivity::class.java)
                lIntent.putExtra(Constants.IMAGE_URL,pPhotoUrl)
                startActivity(lIntent)
            }

            override fun onRemovePhoto(pPosition: Int, pPhotoUrl: String) {
                mViewModel.updatePhoto(pPosition,pPhotoUrl)
            }

            override fun onOptionChoosed(pPosition: Int, pOption: String) {
                mViewModel.updateOption(pPosition,pOption)
            }

            override fun onCommentEnabled(pPosition: Int, pIsSelected: Boolean) {
                mViewModel.updateComment(pPosition,pIsSelected)
            }

        })
        mBinding.recylerView.adapter = mCustomAdapter
    }


    private fun triggerCameraIntent() {
        val lTakePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (lTakePictureIntent.resolveActivity(packageManager) != null) {
            val lTimeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val lImageFileName = "JPEG_$lTimeStamp"
            val lStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            var lFile: File? = null
            try {
                lFile = File.createTempFile(lImageFileName,  ".jpg", lStorageDir)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val photoURI = FileProvider.getUriForFile(
                this, BuildConfig.APPLICATION_ID + ".fileprovider",
                lFile!!
            )
            lTakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            if (lFile != null) {
                mCurrentPhotoPath = lFile.absolutePath
            }
            startActivityForResult(lTakePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(pRequestCode: Int, pResultCode: Int, data: Intent?) {
        super.onActivityResult(pRequestCode, pResultCode, data)
        if(pResultCode == RESULT_OK){
            when(pRequestCode){
                REQUEST_IMAGE_CAPTURE ->{
                    val imageFile = File(mCurrentPhotoPath)
                    val lUri = Uri.parse(imageFile.path)
                    mViewModel.updatePhoto(mPhotoPosition,lUri.toString())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        pRequestCode: Int,
        pPermissions: Array<out String>,
        pGrantResults: IntArray
    ) {
        super.onRequestPermissionsResult(pRequestCode, pPermissions, pGrantResults)
        when(pRequestCode){
            REQ_CODE_PERMISSION_CAMERA -> {
                var lCameraPermissionResult = true
                for (lGrantedResult in pGrantResults) {
                    if (lGrantedResult == PackageManager.PERMISSION_DENIED) {
                        lCameraPermissionResult = false
                    }
                }
                if (lCameraPermissionResult) {
                    triggerCameraIntent()

                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, pPermissions[0])) {
                        showPostRequestAlertDialog(Constants.SETTINGS_INFO)
                    }
                }
            }
        }
    }

    private fun showPostRequestAlertDialog(pExplanation: String?) {
        val lAlertDialog: AlertDialog
        val lAlertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        lAlertDialogBuilder.setTitle("Permission needed").setMessage(pExplanation)
            .setNegativeButton(
                "Not Now"
            ) { _, which -> }.setPositiveButton("SETTINGS"
            ) { _, _ ->
                val lIntent = Intent()
                lIntent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val lUri = Uri.fromParts("package", packageName, null)
                lIntent.data = lUri
                startActivity(lIntent)
            }
        lAlertDialog = lAlertDialogBuilder.create()
        lAlertDialog.show()
    }

}