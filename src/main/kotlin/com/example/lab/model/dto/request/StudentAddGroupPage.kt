package com.example.lab.model.dto.request

import com.example.lab.model.Group
import com.example.lab.model.Student

data class StudentAddGroupPage(
    val student: Student,
    val notUserGroups: List<Group>
)