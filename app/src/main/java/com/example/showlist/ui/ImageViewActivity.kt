package com.example.showlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.showlist.constants.Constants
import com.example.showlist.databinding.ActivityImageViewBinding

class ImageViewActivity  : FragmentActivity(){

    private lateinit var mBinding: ActivityImageViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding  = ActivityImageViewBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.photoUrl = intent.getStringExtra(Constants.IMAGE_URL)
        mBinding.backArrowIv.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}