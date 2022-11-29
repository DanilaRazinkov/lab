package com.example.lab.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "subject")
data class Subject(
    @Id
    @GeneratedValue()
    var id: Long,

    @Column(nullable = false)
    var name: String
)
