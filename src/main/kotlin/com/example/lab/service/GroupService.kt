package com.example.lab.service

import com.example.lab.repository.GroupRepository
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val groupRepository: GroupRepository
) {
}