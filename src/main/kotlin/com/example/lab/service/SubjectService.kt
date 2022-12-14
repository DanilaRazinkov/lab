package com.example.lab.service

import com.example.lab.model.Subject
import com.example.lab.repository.SubjectRepository
import org.springframework.stereotype.Service

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository
) {
    fun loadOrCreatByName(subjectName: String): Subject {
        return subjectRepository.findByName(subjectName).orElseGet {
            val newSubject = Subject(name = subjectName)
            subjectRepository.save(newSubject)
        }
    }
}