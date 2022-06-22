package com.example.showlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.showlist.datamodel.DataModel
import com.example.showlist.repository.CustomRepository

class CustomViewModel(pApplication: Application) : AndroidViewModel(pApplication) {
    private val mCustomRepo = CustomRepository()

    private val _ItemList = MutableLiveData<List<DataModel>>()
    val mItemList: LiveData<List<DataModel>> = _ItemList
    var mList = ArrayList<DataModel>()

    private val _OnItemChanged = MutableLiveData<Int>()
    val mOnItemChanged: LiveData<Int> = _OnItemChanged

    fun getItemList() {
        mList.addAll(mCustomRepo.getItemList())
        _ItemList.value = mList
    }

    fun updateOption(pPosition: Int, pOption: String) {
        mList[pPosition].selectedOption = pOption
        _ItemList.value = mList
        _OnItemChanged.value = pPosition
    }

    fun updateComment(pPosition: Int, pIsChecked: Boolean) {
        mList[pPosition].isChecked = pIsChecked
        _ItemList.value = mList
    }

    fun updatePhoto(pPosition: Int, pPhotoUrl: String) {
        mList[pPosition].photoUrl = pPhotoUrl
        _ItemList.value = mList
        _OnItemChanged.value = pPosition
    }

}