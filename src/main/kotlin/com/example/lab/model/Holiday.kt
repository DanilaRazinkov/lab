package com.example.lab.model

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "holiday")
data class Holiday(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    var id: Long? = null,

    var date: LocalDate,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "semester_id")
    var semester: Semester
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Holiday

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , date = $date , semester = $semester )"
    }
}