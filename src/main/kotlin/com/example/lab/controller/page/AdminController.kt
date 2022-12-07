package com.example.lab.controller.page

import com.example.lab.model.dto.request.UserCreateRequest
import com.example.lab.model.dto.response.AdminPageResponse
import com.example.lab.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class AdminController(
    private val userService: UserService
) {
    @GetMapping("/admin")
    fun adminPage(model: Model): String {
        model.addAttribute("pageDto", AdminPageResponse(
            userService.findAll()
        ))
        return "admin"
    }
}