package com.example.lab.repository

import com.example.lab.model.Occupation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface OccupationRepository : JpaRepository<Occupation, Long> {
    fun findAllByTeacherIdOrderByDateAscHour(id: UUID): List<Occupation>
    fun findAllByDateAndHourAndTeacherId(date: LocalDate, hour: Int, teacher_id: UUID): List<Occupation>
    fun findAllByAudience(audience: String): List<Occupation>
}