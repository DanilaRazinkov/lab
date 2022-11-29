package com.example.lab.model

import com.example.lab.model.enums.Role
import org.hibernate.Hibernate
import javax.persistence.*

@Entity(name = "user_role")
data class UserRole(
    @Id
    @GeneratedValue
    var id: Long,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @ManyToMany(fetch = FetchType.LAZY)
    var usersWithRole: MutableList<User>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserRole

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , role = $role )"
    }
}