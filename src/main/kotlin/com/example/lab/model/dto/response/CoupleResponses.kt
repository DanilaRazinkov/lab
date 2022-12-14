package com.example.lab.model.dto.response

import com.example.lab.model.Couple
import com.example.lab.model.dto.request.CoupleDto

data class BaseCoupleResponses(
    var mon: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var tue: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var wen: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var thu: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var fri: MutableMap<Int, CoupleDto>? = mutableMapOf(),
    var sun: MutableMap<Int, CoupleDto>? = mutableMapOf()
)