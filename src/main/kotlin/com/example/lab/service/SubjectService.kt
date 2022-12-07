package com.example.lab.service

import com.example.lab.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository
) {
}