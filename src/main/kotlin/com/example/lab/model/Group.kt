package com.example.lab.model

import javax.persistence.*

@Entity(name = "group")
data class Group(
    @Id
    @GeneratedValue
    var id: Long,

    @Column(nullable = false)
    var number: Int,

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
    var student: MutableList<Student>
)