package com.example.lab.service

import com.example.lab.model.UserRole
import com.example.lab.model.enums.Role
import com.example.lab.repository.UserRoleRepository
import org.springframework.stereotype.Service

@Service
class UserRoleService(
    private val userRoleRepository: UserRoleRepository
) {

    fun loadBaseRole(): UserRole {
        return userRoleRepository.findByRole(Role.BASE)
            .orElseGet {
                val newUserBaseRole = UserRole(
                    role = Role.BASE
                )
                userRoleRepository.save(newUserBaseRole)
            }
    }
}