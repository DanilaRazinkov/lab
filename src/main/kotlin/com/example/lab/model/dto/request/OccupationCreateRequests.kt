package com.example.lab.model.dto.request

import java.time.LocalDate

data class OccupationCreateRequests(
    var hour: Int = 1,
    var date: String = "",
    var subjectName: String? = null,
    val audience: String? = null,
)