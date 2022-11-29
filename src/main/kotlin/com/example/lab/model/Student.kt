package com.example.lab.model

import java.util.*
import javax.persistence.*

@Entity(name = "student")
data class Student(
    @Id
    @GeneratedValue
    var id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group,

    @OneToOne(optional = false, fetch = FetchType.EAGER, mappedBy = "student")
    @JoinColumn(name = "user_id")
    var user: User
)
