package com.example.lab.model

import com.example.lab.model.enums.Period
import org.hibernate.Hibernate
import javax.persistence.*

@Entity(name = "couple")
data class Couple(
    @Id
    @GeneratedValue
    var id: Long,

    @Column(nullable = false)
    var hour: Int,

    @Enumerated(EnumType.STRING)
    var period: Period,

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    var subject: Subject,

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher,

    @ManyToMany
    @JoinTable(
        name = "couple_group",
        joinColumns = [JoinColumn(name = "couple_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    var groups: MutableList<Group>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Couple

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , hour = $hour , period = $period , subject = $subject , teacher = $teacher )"
    }
}
