package com.example.lab.model

import com.example.lab.model.enums.Period
import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "semester")
data class Semester(
    @Id
    @GeneratedValue
    var id: Long,

    @Column(nullable = false)
    var semesterStart: LocalDate,

    @Column(nullable = false)
    var semesterEnd: LocalDate,

    @OneToMany(mappedBy = "semester")
    var couples: MutableList<Couple>,

    @OneToMany(mappedBy = "semester")
    var holidays: MutableList<Holiday>

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Semester

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , semesterStart = $semesterStart , semesterEnd = $semesterEnd)"
    }
}
