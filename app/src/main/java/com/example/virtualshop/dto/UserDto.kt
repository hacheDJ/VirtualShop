package com.example.virtualshop.dto

data class UserDto(
    val idUser: Int,
    val nameUser: String,
    val lastNameUser: String,
    val gender: String,
    val documentNumber: String,
    val documentType: String,
    val address: String,
    val email: String,
    val urlImg: String?,
    val role: String,
    val state: String

)