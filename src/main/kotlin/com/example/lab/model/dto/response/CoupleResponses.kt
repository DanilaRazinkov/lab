package com.example.lab.model.dto.response

import com.example.lab.model.Couple
import com.example.lab.model.dto.request.CoupleDto
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

data class BaseCoupleResponses(
    var mon: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var tue: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var wen: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var thu: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var fri: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var sun: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var today: DayOfWeek? = null,
    var todayT: LocalDate = LocalDate.now(),
    var hidenToday: LocalDate = LocalDate.now(),
    var group: Long = 0,
    var firstDay: LocalDate = hidenToday.with(DayOfWeek.MONDAY)
)

data class TeacherCoupleResponses(
    var mon: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var tue: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var wen: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var thu: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var fri: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var sun: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var today: DayOfWeek? = null,
    var todayT: LocalDate = LocalDate.now(),
    var hidenToday: LocalDate = LocalDate.now(),
    var firstDay: LocalDate = hidenToday.with(DayOfWeek.MONDAY)
)