package com.example.musicapp.models

import com.google.gson.annotations.SerializedName

data class Song (
    @field:SerializedName("id")
    var id: Int = 0,
    @field:SerializedName("name") //fieldName
    var name: String = "",
    @field:SerializedName("quantity") //field2
    var quantity: Int = 0,
    @field:SerializedName("price") //field5
    var price :Int = 0,
    @field:SerializedName("status") //field3
    var status: String = "new")