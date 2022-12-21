package com.example.lab.model.dto.request

import com.example.lab.model.Couple
import com.example.lab.model.Group
import com.example.lab.model.Subject
import com.example.lab.model.Teacher

data class CoupleDto(
    val subject: Subject,
    val audience: String,
    val teacher: Teacher,
    val groups: List<Group>
)

fun Couple.toDTO() =
    CoupleDto(
        subject = subject!!,
        audience = audience,
        teacher = teacher!!,
        groups = groups
    )