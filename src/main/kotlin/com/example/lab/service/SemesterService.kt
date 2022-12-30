package com.example.lab.service

import com.example.lab.exceptions.NotFoundException
import com.example.lab.model.Semester
import com.example.lab.model.Teacher
import com.example.lab.repository.SemesterRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import java.util.*


@Service
class SemesterService(
    private val semesterRepository: SemesterRepository
) {

    fun findAll(): MutableList<Semester> = semesterRepository.findAll()

    fun findById(uuid: Long): Semester {
        return semesterRepository.findById(uuid).orElseThrow { NotFoundException("") }
    }
}