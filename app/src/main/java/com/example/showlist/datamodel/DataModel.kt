package com.example.showlist.datamodel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class DataModel(
    @JsonProperty("type")
    var type: String,
    @JsonProperty("id")
    var id: String,
    @JsonProperty("title")
    var title: String,
    @JsonProperty("dataMap")
    var dataMap: DataMap = DataMap(ArrayList<String>())
    ){

    var photoUrl        = ""
    var selectedOption  = ""
    var isChecked       = false

}