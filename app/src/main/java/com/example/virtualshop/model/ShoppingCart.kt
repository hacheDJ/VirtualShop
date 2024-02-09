package com.example.virtualshop.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tb_ShoppingCart")
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idProduct: Int,
    val nameProduct: String,
    val urlImg: String,
    val unitPrice: Double,
    val quantity: Int
): Parcelable
