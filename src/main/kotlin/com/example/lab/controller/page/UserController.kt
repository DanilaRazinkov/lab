package com.example.lab.controller.page

import com.example.lab.model.dto.request.toCreatRequest
import com.example.lab.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Controller
class UserController(
    private val userService: UserService
) {

    @GetMapping("/users/{userId}")
    fun userPage(@PathVariable userId: UUID, model: Model): String {
        val user = userService.findUserById(userId)
        model.addAttribute("user", user)
        model.addAttribute("teacher", user.teacher.toCreatRequest())
        return "user-page"
    }

    @GetMapping("/users/my")
    fun userMyPage(model: Model): String {
        val user = userService.findCurrentUser()
        model.addAttribute("user", userService.findCurrentUser())
        model.addAttribute("teacher", user.teacher.toCreatRequest())
        return "user-page"
    }

}