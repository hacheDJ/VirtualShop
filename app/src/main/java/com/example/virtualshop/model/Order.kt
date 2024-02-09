package com.example.virtualshop.model

data class Order(
    val orderState: String,
    val deliveryDate: String,
    val orderDate: String,
    val idOrder: Int,
    val seller: String,
    val total: Double,
    val idProofOfPay: Int

)
