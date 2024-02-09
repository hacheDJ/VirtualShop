package com.example.virtualshop.dto

data class ProofOfPayDetailReq(
    val idProduct: Int,
    val quantity: Int,
    val salePrice: Double
)