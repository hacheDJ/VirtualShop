package com.example.virtualshop.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("idProduct")
    val idProduct : Int ,
    @SerializedName("nameProduct")
    val nameProduct : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("urlImg")
    val urlImg : String,
    @SerializedName("unitPrice")
    val unitPrice : Double,
    @SerializedName("creationDate")
    val creationDate : String,
    @SerializedName("modificationDate")
    val modificationDate : String,
    @SerializedName("stock")
    val stock : Int,
    @SerializedName("idCategory")
    val idCategory : String
): Parcelable
