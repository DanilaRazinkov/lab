package com.example.lab.controller.page

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
        model.addAttribute("user", userService.findUserById(userId))
        return "user-page"
    }

    @GetMapping("/users/my")
    fun userMyPage(model: Model): String {
        model.addAttribute("user", userService.findCurrentUser())
        return "user-page"
    }

}