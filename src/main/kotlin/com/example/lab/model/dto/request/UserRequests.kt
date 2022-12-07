package com.example.lab.model.dto.request

data class UserCreateRequest(
    val email: String = "",
    val surname: String = "",
    val name: String = "",
    val patronymic: String= "",
    val password: String = ""
)