package com.example.virtualshop.dto

data class UserDetailDto(
    val idUser: Int,
    val nameUser: String,
    val lastNameUser: String,
    val gender: String,
    val documentType: String,
    val documentNumber: String,
    val address: String,
    val email: String,
    val urlImg: String,
    val role: String,
    val state: String,
    val creationDate: String
)