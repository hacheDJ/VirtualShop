package com.example.virtualshop.dto

data class DetailOfUserRes(
    val err: Boolean,
    val data: UserDetailDto,
    val msg: String
)