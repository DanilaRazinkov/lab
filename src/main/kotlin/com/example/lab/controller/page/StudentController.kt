package com.example.lab.controller.page

import com.example.lab.service.StudentService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Controller
class StudentController(
    private val studentService: StudentService
) {

    @PostMapping("/students")
    fun studentCreate(@RequestParam userId: UUID): String {
        studentService.createStudent(userId)
        return "redirect:/users/${userId}"
    }
}