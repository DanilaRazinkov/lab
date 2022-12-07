package com.example.lab.service

import com.example.lab.repository.TeacherRepository
import org.springframework.stereotype.Service

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository
) {
}