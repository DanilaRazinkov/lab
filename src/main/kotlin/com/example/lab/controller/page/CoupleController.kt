package com.example.lab.controller.page

import com.example.lab.model.dto.response.BaseCoupleResponses
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CoupleController {

    @GetMapping("/my/couple")
    fun loadMyCouple(model: Model): String {
        model.addAttribute("dto", BaseCoupleResponses())
        return "base_couple"
    }
}