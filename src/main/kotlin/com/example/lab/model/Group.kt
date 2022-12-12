package com.example.lab.model

import org.hibernate.Hibernate
import org.springframework.stereotype.Indexed
import javax.persistence.*

@Entity(name = "student_group")
@Indexed
data class Group(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var number: Int,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
    var student: MutableList<Student> = mutableListOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Group

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , number = $number )"
    }
}