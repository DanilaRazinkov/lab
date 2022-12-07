package com.example.lab.repository

import com.example.lab.model.UserRole
import com.example.lab.model.enums.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long> {
    fun findByRole(role: Role): Optional<UserRole>
}