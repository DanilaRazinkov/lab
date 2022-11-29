package com.example.lab.controller.page

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping


class IndexPageController {
    @RequestMapping("/login")
    fun login(): String {
        return "templates/index.html"
    }

    @RequestMapping("/login-error")
    fun loginError(model: Model): String {
        model.addAttribute("loginError", true)
        return "templates/index.html"
    }
}