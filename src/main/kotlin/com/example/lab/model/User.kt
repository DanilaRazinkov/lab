package com.example.lab.model

import com.example.lab.model.enums.Role
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity(name = "base_user")
data class User(
    @Id
    @GeneratedValue
    @Column(nullable = false)
    var id: UUID? = null,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var surname: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var patronymic: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_to_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<UserRole>,

    @OneToOne(mappedBy = "user")
    var student: Student? = null,

    @OneToOne
    var teacher: Teacher? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $email )"
    }
}
