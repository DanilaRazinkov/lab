package com.example.lab.model.dto.request

import com.example.lab.model.Teacher

data class TeacherCreateRequests(
    var position: String? = null,
    var degree: String? = null,
    var experience: Int? = null
)

fun Teacher?.toCreatRequest() =
    TeacherCreateRequests(
        position = this?.position,
        degree = this?.degree,
        experience = this?.experience,
    )