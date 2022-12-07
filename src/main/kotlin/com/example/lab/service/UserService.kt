package com.example.lab.service

import com.example.lab.exceptions.NotFoundException
import com.example.lab.extentions.transformes.toDetails.toUserDetails
import com.example.lab.model.User
import com.example.lab.model.UserRole
import com.example.lab.model.dto.request.UserCreateRequest
import com.example.lab.model.enums.Role
import com.example.lab.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    @Lazy
    private val encoder: BCryptPasswordEncoder,
    private val userRoleService: UserRoleService
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun loadUserByUsername(email: String): UserDetails {
        log.info("Loading user details by email: $email")
        val user = userRepository.findByEmail(email).orElseThrow {
            log.warn("User not found by email: $email")
            throw NotFoundException("User not found by email: $email")
        }
        return user.toUserDetails()
    }

    fun createUser(userCreateRequest: UserCreateRequest) {
        log.info("Creating user by request: $userCreateRequest")
        val email = userCreateRequest.email
        userRepository.findByEmail(email).ifPresent {
            log.warn("User with this email exists")
            throw UnsupportedOperationException("User with this email exists")
        }
        val newUser = User(
            email = email,
            password = encoder.encode(userCreateRequest.password),
            surname = userCreateRequest.surname,
            patronymic = userCreateRequest.patronymic,
            name = userCreateRequest.name,
            roles = mutableSetOf(userRoleService.loadBaseRole())
        )
        userRepository.save(newUser)
    }

}