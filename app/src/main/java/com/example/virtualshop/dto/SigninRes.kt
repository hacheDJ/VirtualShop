package com.example.virtualshop.dto

data class SigninRes(

    val err: Boolean,
    val data: UserDto,
    val token: String,
    val msg: String
)