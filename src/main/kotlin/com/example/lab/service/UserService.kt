package com.example.lab.service

import com.example.lab.exceptions.NotFoundException
import com.example.lab.extentions.transformes.toDetails.toUserDetails
import com.example.lab.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun loadUserByUsername(email: String): UserDetails {
        log.info("Loading user details by email: $email")
        val user = userRepository.findByEmail(email).orElseThrow{
            log.warn("User not found by email: $email")
            throw NotFoundException("User not found by email: $email")
        }
        return user.toUserDetails()
    }

}