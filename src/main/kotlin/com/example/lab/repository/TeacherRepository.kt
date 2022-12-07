package com.example.lab.repository

import com.example.lab.model.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TeacherRepository : JpaRepository<Teacher, UUID>