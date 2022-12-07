package com.example.lab.service

import com.example.lab.repository.CoupleRepository
import org.springframework.stereotype.Service

@Service
class CoupleService(
    private val coupleRepository: CoupleRepository
) {
}