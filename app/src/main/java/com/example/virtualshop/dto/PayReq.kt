package com.example.virtualshop.dto

data class PayReq(
    val cardType: String,
    val cardNumber: String,
    val holder: String,
    val dateOfExpiry: String,
    val cvv: String,
    val passCard: String,
    val totalAmount: Double,
    val listProofOfPayDetail: List<ProofOfPayDetailReq>,
    val placeOfDelivery: String,
    val deliveryDate: String,
    val contactNumber: String

)