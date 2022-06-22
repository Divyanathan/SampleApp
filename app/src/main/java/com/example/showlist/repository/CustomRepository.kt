package com.example.showlist.repository

import android.util.Log
import com.example.showlist.datamodel.DataModel
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.*

class CustomRepository {


    private var mItemList =  ArrayList<DataModel>()
    init {
        val lObjectMapper = ObjectMapper()
        try {

            var list1 = lObjectMapper.readValue<ArrayList<DataModel>>(
                "[ \n" +
                        "{ \n" +
                        "\"type\": \"PHOTO\", \n" +
                        "\"id\": \"pic1\", \n" +
                        "\"title\": \"Photo 1\", \n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"SINGLE_CHOICE\", \n" +
                        "\"id\": \"choice1\", \n" +
                        "\"title\": \"Photo 1 choice\", \n" +
                        "\"dataMap\": { \n" +
                        "\"options\": [ \n" +
                        "\"Good\", \n" +
                        "\"OK\", \n" +
                        "\"Bad\" \n" +
                        "] \n" +
                        "} \n" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"COMMENT\", \n" +
                        "\"id\": \"comment1\", \n" +
                        "\"title\": \"Photo 1 comments\", \n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"PHOTO\", \n" +
                        "\"id\": \"pic2\", \n" +
                        "\"title\": \"Photo 2\", \n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"SINGLE_CHOICE\", \n" +
                        "\"id\": \"choice2\", \n" +
                        "\"title\": \"Photo 2 choice\", \n" +
                        "\"dataMap\": { \n" +
                        "\"options\": [ \n" +
                        "\"Good\", \n" +
                        "\"OK\", \n" +
                        "\"Bad\" \n" +
                        "] \n" +
                        "} \n" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"COMMENT\", \n" +
                        "\"id\": \"comment2\", \n" +
                        "\"title\": \"Photo 2 comments\",\n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"COMMENT\", \n" +
                        "\"id\": \"comment3\", \n" +
                        "\"title\": \"Photo 3 comments\", \n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"PHOTO\", \n" +
                        "\"id\": \"pic3\", \n" +
                        "\"title\": \"Photo 3\", \n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"SINGLE_CHOICE\", \n" +
                        "\"id\": \"choice2\", \n" +
                        "\"title\": \"Photo 3 choice\", \n" +
                        "\"dataMap\": { \n" +
                        "\"options\": [ \n" +
                        "\"Nice\", \n" +
                        "\"Not Nice\" \n" +
                        "] \n" +
                        "} \n" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"SINGLE_CHOICE\", \n" +
                        "\"id\": \"choice4\", \n" +
                        "\"title\": \"Photo 4 type\", \n" +
                        "\"dataMap\": { \n" +
                        "\"options\": [ \n" +
                        "\"Document\", \n" +
                        "\"Face\" \n" +
                        "] \n" +
                        "} \n" +
                        "}, \n" +
                        "{ \n" +
                        "\"type\": \"PHOTO\", \n" +
                        "\"id\": \"pic4\", \n" +
                        "\"title\": \"Photo 4\", \n" +
                        "\"dataMap\": {\"options\":[]}" +
                        "} \n" +
                        "] \n",
                object : TypeReference<ArrayList<DataModel>>() {})
            Log.d("CustomRepository", "Item list1: $list1")

            mItemList.addAll(list1)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getItemList():ArrayList<DataModel>{
        return mItemList
    }
}


