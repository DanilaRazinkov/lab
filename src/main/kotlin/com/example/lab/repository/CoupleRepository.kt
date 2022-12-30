package com.example.lab.repository

import com.example.lab.model.Couple
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CoupleRepository : JpaRepository<Couple, Long> {
    fun findAllByAudience(audience: String): List<Couple>
}