package com.example.lab.model

import com.example.lab.model.dto.request.CoupleDto
import com.example.lab.model.enums.Period
import org.hibernate.Hibernate
import java.time.DayOfWeek
import java.time.LocalDate
import javax.persistence.*

@Entity(name = "occupation")
data class Occupation(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var hour: Int,

    var date: LocalDate,

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher,

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST]
    )
    @JoinTable(
        name = "occupation_group",
        joinColumns = [JoinColumn(name = "occupation_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableList<Group> = mutableListOf(),

    @Column(nullable = false)
    var audience: String,
    @Column(nullable = false)
    var subject: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Couple

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
    override fun toString(): String {
        return "Occupation(id=$id, hour=$hour, date=$date, teacher=$teacher, groups=$groups, audience='$audience')"
    }

    fun toDTO(): CoupleDto {
        return CoupleDto(
            subject = Subject(name = subject),
            audience = audience,
            teacher = teacher,
            groups = groups
        )
    }

}