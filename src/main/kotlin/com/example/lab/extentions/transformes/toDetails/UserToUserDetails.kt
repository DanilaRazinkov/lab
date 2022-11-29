package com.example.lab.extentions.transformes.toDetails

import com.example.lab.model.User
import com.example.lab.model.details.UserDetailsBase

fun User.toUserDetails() =
    UserDetailsBase(
        id = id,
        email = email,
        userPassword = password,
        roles = roles.map { it.role }.toSet()
    )