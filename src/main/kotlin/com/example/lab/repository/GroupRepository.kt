package com.example.lab.repository

import com.example.lab.model.Group
import com.example.lab.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    fun findAllByStudentNotContaining(student: Student): List<Group>
}