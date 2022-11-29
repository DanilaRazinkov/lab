package com.example.lab.model

import com.example.lab.model.enums.Period
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
)
