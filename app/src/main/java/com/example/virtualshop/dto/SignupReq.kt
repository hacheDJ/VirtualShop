package com.example.virtualshop.dto

data class SignupReq(
    val nameUser: String,
    val lastNameUser: String,
    val gender: String,
    val documentType: String,
    val documentNumber: String,
    val address: String,
    val email: String,
    val passwordUser: String,
    val confPass: String
)