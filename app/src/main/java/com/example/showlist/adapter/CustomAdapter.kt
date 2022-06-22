package com.example.showlist.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.showlist.constants.Constants
import com.example.showlist.databinding.CommentViewBinding
import com.example.showlist.databinding.OptionViewBinding
import com.example.showlist.databinding.PhotoViewBinding
import com.example.showlist.datamodel.DataModel

class CustomAdapter(private val pCustomListener: CustomListener) :
    ListAdapter<DataModel, CustomAdapter.CustomViewHolder>(CustomDiffCallBack()) {

    private val PHOTO_VIEW_TYPE = 1
    private val OPTION_VIEW_TYPE = 2
    private val COMMENT_VIEW_TYPE = 3
    val mCustomListener =pCustomListener

    companion object {
        private val TAG = "CustomAdapter"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")
        return when (viewType) {
            PHOTO_VIEW_TYPE -> {
                var lMessageView =
                    PhotoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PhotoView(lMessageView)
            }

            OPTION_VIEW_TYPE -> {
                var lOptionView =
                    OptionViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                OptionView(lOptionView)
            }

            else -> {
                var lCommentView =
                    CommentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CommentView(lCommentView)
            }
        }

    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position),position)
        Log.d(TAG, "onBindViewHolder: ")
    }


    abstract class CustomViewHolder(pView: View) : RecyclerView.ViewHolder(pView) {
        abstract fun bind(item: DataModel, pPosition: Int)
    }

    inner class PhotoView(private val pPhotoBinding: PhotoViewBinding) :
        CustomViewHolder(pPhotoBinding.root) {

        override fun bind(pDataModel: DataModel, pPosition: Int) {
            pPhotoBinding.apply {
                dataModel = pDataModel
                if (pDataModel.photoUrl.isEmpty()){
                    closeIv.visibility = View.GONE
                }else{
                    closeIv.visibility = View.VISIBLE
                    closeIv.setOnClickListener {
                        pCustomListener.onRemovePhoto(pPosition,"")
                        closeIv.visibility = View.GONE
                    }
                }

                imageIv.setOnClickListener {
                    if (pDataModel.photoUrl.isEmpty()){
                        mCustomListener.onPhotoClicked(pPosition)
                    }else{
                        mCustomListener.onOpenImagePage(pPhotoUrl = pDataModel.photoUrl)
                    }
                }
            }
        }
    }



    inner class OptionView(private val pOptionViewBinding: OptionViewBinding) :
        CustomViewHolder(pOptionViewBinding.root) {

        override fun bind(pDataModel: DataModel, pPosition: Int) {
            pOptionViewBinding.apply {
                dataModel = pDataModel
                radioButton1.visibility = View.GONE
                radioButton2.visibility = View.GONE
                radioButton3.visibility = View.GONE

                for (i in 0 until pDataModel.dataMap.options.size) {
                    when (i) {
                        0 -> {
                            radioButton1.visibility = View.VISIBLE
                            radioButton1.text = pDataModel.dataMap.options[i]
                            if (pDataModel.selectedOption == pDataModel.dataMap.options[i]){
                                radioButton1.isChecked = true
                            }
                        }
                        1 -> {
                            radioButton2.visibility = View.VISIBLE
                            radioButton2.text = pDataModel.dataMap.options[i]
                            if (pDataModel.selectedOption == pDataModel.dataMap.options[i]){
                                radioButton1.isChecked = true
                            }
                        }
                        2 -> {
                            radioButton3.visibility = View.VISIBLE
                            radioButton3.text = pDataModel.dataMap.options[i]
                            if (pDataModel.selectedOption == pDataModel.dataMap.options[i]){
                                radioButton1.isChecked = true
                            }
                        }
                    }

                }
            }
        }
    }

    inner class CommentView(private val pCommentViewBinding: CommentViewBinding) :
        CustomViewHolder(pCommentViewBinding.root) {

        override fun bind(pDataModel: DataModel, pPosition: Int) {
            pCommentViewBinding.apply {
                dataModel = pDataModel
                toggleButton.isChecked = pDataModel.isChecked
                toggleButton.setOnCheckedChangeListener { _, isChecked ->
                    pCustomListener.onCommentEnabled(pPosition = pPosition, isChecked)
                    if (isChecked){
                        commentText.visibility = View.VISIBLE
                    }else{
                        commentText.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            "PHOTO" -> {
                PHOTO_VIEW_TYPE
            }
            "SINGLE_CHOICE" -> {
                OPTION_VIEW_TYPE
            }
            else -> {
                COMMENT_VIEW_TYPE
            }
        }
    }


    class CustomDiffCallBack : DiffUtil.ItemCallback<DataModel>() {
        override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
            return oldItem == newItem
        }
    }

    interface CustomListener {
        fun onPhotoClicked(pPosition: Int)
        fun onOpenImagePage(pPhotoUrl: String)
        fun onRemovePhoto(pPosition: Int,pPhotoUrl: String)
        fun onOptionChoosed(pPosition: Int,pOption:String)
        fun onCommentEnabled(pPosition: Int,pIsSelected :Boolean)
    }


}