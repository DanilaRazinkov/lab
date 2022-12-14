package com.example.lab.service

import com.example.lab.model.Teacher
import com.example.lab.model.dto.request.TeacherCreateRequests
import com.example.lab.repository.TeacherRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository,
    private val userService: UserService
) {
    fun updateTeacher(userId: UUID, teacherCreateRequests: TeacherCreateRequests) {
        val user = userService.findUserById(userId)
        val teacher = Teacher(
            id = userId,
            position = teacherCreateRequests.position!!,
            degree = teacherCreateRequests.degree!!,
            experience = teacherCreateRequests.experience!!,
            user = user
        )
        user.teacher = teacherRepository.save(teacher)
        userService.saveUser(user)
    }

    fun findAll() = teacherRepository.findAll()
    fun findById(uuid: UUID): Teacher {
        return teacherRepository.findById(uuid).orElseThrow { NotFoundException() }
    }
}