package com.example.lab.model.details

import com.example.lab.model.enums.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserDetailsBase(
    val id: UUID,
    val email: String,
    val userPassword: String,
    val roles: Set<Role>
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority("ROLE_${it.name}") }
    }

    override fun getPassword() = userPassword

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}