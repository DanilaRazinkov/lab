package com.example.lab.model

import org.hibernate.Hibernate
import org.springframework.stereotype.Indexed
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "subject")
@Indexed
data class Subject(
    @Id
    @GeneratedValue()
    var id: Long? = null,

    @Column(nullable = false)
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Subject

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}
