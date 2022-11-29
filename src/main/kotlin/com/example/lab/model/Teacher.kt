package com.example.lab.model

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity(name = "teacher")
data class Teacher(
    @Id
    @GeneratedValue
    var id: UUID,

    @Column(nullable = false)
    var position: String,

    @Column(nullable = false)
    var experience: Int,

    @Column(nullable = false)
    var degree: String,

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Teacher

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , position = $position , experience = $experience , degree = $degree )"
    }

}