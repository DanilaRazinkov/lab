package com.example.lab.service

import com.example.lab.model.Group
import com.example.lab.model.Student
import com.example.lab.repository.StudentRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val userService: UserService
) {

    fun createStudent(userId: UUID) {
        val user = userService.findUserById(userId)
        val newStudent = Student(
            user = user
        )
        studentRepository.save(newStudent)
    }

    fun findStudentById(studentId: UUID): Student {
        return studentRepository.findById(studentId)
            .orElseThrow{
                throw NotFoundException()
            }
    }

    fun addStudentInGroup(studentId: UUID, group: Group): Student {
        val student = findStudentById(studentId)
        student.group = group
        return studentRepository.save(student)
    }

    fun removeStudentFromGroup(studentId: UUID,): Student {
        val student = findStudentById(studentId)
        student.group = null
        return studentRepository.save(student)
    }
}