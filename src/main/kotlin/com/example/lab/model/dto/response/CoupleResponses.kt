package com.example.lab.model.dto.response

import com.example.lab.model.Couple

data class BaseCoupleResponses (
    val mon: List<Couple>? = null,
    val tue: List<Couple>? = null,
    val wen: List<Couple>? = null,
    val thu: List<Couple>? = null,
    val fri: List<Couple>? = null,
    val sun: List<Couple>? = null
)