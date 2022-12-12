package com.example.lab.controller.page

import com.example.lab.model.dto.request.UserCreateRequest
import com.example.lab.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping


@Controller
class IndexPageController(
    private val userService: UserService
) {
    @GetMapping("/login")
    fun login(): String {
        return "login.html"
    }

    @GetMapping("/sign-up")
    fun signUp(model: Model): String {
        val userCreateRequest = UserCreateRequest()
        model.addAttribute("userCreateRequest", userCreateRequest)
        return "sign-up.html"
    }

    @GetMapping("/index")
    fun indexPage(): String {
        return "index.html"
    }

    @GetMapping("/login-error")
    fun loginError(model: Model): String {
        model.addAttribute("loginError", true);
        return "login.html"
    }

    @PostMapping("/sign-up")
    fun signUpTest(@ModelAttribute("userCreateRequest") userCreateRequest: UserCreateRequest, model: Model): String {
        try {
            userService.createUser(userCreateRequest)
        } catch (e: Exception) {
            model.addAttribute("signUpError", e.message)
            return "sign-up.html"
        }
        return "redirect:/index.html"
    }
}