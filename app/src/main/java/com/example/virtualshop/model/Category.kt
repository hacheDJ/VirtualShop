package com.example.virtualshop.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    val idCategory: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("creationDate")
    val creationDate: String,
    @SerializedName("modificationDate")
    val modificationDate: String
)
