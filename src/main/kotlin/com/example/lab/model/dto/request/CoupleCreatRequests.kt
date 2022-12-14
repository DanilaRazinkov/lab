package com.example.lab.model.dto.request

import com.example.lab.model.Semester
import com.example.lab.model.Teacher
import com.example.lab.model.enums.Period
import java.time.DayOfWeek
import java.util.*

data class CoupleCreatRequests(
    var hour: Int = 1,
    val subjectName: String? = null,
    val period: Period = Period.FULL,
    val audience: String? = null,
    val semesterId: Long? = null,
    val teacherId: UUID? = null,
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY
)