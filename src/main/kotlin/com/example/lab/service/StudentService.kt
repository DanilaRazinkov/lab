package com.example.lab.service

import com.example.lab.repository.StudentRepository
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
}