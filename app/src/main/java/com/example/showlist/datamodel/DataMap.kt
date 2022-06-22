package com.example.showlist.datamodel

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class DataMap(
    @JsonProperty("options")
    var options: ArrayList<String>) {
}