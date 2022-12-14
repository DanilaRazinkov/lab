package com.example.lab.controller.page

import com.example.lab.model.dto.request.TeacherCreateRequests
import com.example.lab.service.TeacherService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

@Controller
class TeacherController(
    private val teacherService: TeacherService
) {

    @PostMapping("/teachers/{userId}/update")
    fun updateTeacher(
        @PathVariable userId: UUID,
        @ModelAttribute("teacher") teacherCreateRequests: TeacherCreateRequests
    ): String {
        teacherService.updateTeacher(userId, teacherCreateRequests)
        return "redirect:/users/${userId}"
    }
}