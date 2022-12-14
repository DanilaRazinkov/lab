package com.example.lab.repository

import com.example.lab.model.Semester
import com.example.lab.model.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SemesterRepository : JpaRepository<Semester, Long>