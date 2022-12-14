package com.example.lab.controller.page

import com.example.lab.model.dto.request.CoupleCreatRequests
import com.example.lab.model.dto.response.BaseCoupleResponses
import com.example.lab.service.CoupleService
import com.example.lab.service.GroupService
import com.example.lab.service.SemesterService
import com.example.lab.service.TeacherService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CoupleController(
    private val coupleService: CoupleService,
    private val teacherService: TeacherService,
    private val semesterService: SemesterService,
    private val groupService: GroupService
) {

    @GetMapping("/couples/my")
    fun loadMyCouple(model: Model): String {
        model.addAttribute("dto", BaseCoupleResponses())
        return "base_couple"
    }

    @GetMapping("/couples")
    @PreAuthorize("hasRole('ADMIN')")
    fun loadCreatCouplePage(model: Model): String {
        model.addAttribute("dto", CoupleCreatRequests(hour = 1))
        model.addAttribute("teachers", teacherService.findAll())
        model.addAttribute("semesters", semesterService.findAll())
        return "couple-create-page"
    }

    @GetMapping("/couples/{coupleId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun loadCreatCoupleByIdPage(model: Model, @PathVariable coupleId: Long): String {
        val couple = coupleService.loadById(coupleId)
        model.addAttribute(
            "dto", CoupleCreatRequests(
                hour = couple.hour,
                period = couple.period,
                subjectName = couple.subject.name,
                teacherId = couple.teacher.id,
                semesterId = couple.semester.id,
                audience = couple.audience,
                dayOfWeek = couple.day
            )
        )
        model.addAttribute("groups", groupService.findAll().distinct())
        model.addAttribute("couple", couple)
        model.addAttribute("teachers", teacherService.findAll().distinct())
        model.addAttribute("semesters", semesterService.findAll().distinct())
        return "couple-page"
    }

    @PostMapping("/couples/{coupleId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCreatCoupleByIdPage(
        @ModelAttribute("dto") dto: CoupleCreatRequests,
        @PathVariable coupleId: Long
    ): String {
        coupleService.updateCouple(coupleId, dto)
        return "redirect:/couples/${coupleId}"
    }

    @PostMapping("/couples/{coupleId}/groups/{groupId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun addGroup(
        @PathVariable coupleId: Long, @PathVariable groupId: Long
    ): String {
        coupleService.addGroup(coupleId, groupId)
        return "redirect:/couples/${coupleId}"
    }

    @PostMapping("/couples/{coupleId}/groups/{groupId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    fun removeGroup(
        @PathVariable coupleId: Long, @PathVariable groupId: Long
    ): String {
        coupleService.removeGroup(coupleId, groupId)
        return "redirect:/couples/${coupleId}"
    }

    @PostMapping("/couples/{coupleId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCreatCoupleByIdPage(
        @PathVariable coupleId: Long
    ): String {
        coupleService.deleteCouple(coupleId)
        return "redirect:/index"
    }

    @PostMapping("/couples")
    fun creatCouple(@ModelAttribute("dto") dto: CoupleCreatRequests): String {
        val id = coupleService.createCouple(dto)
        return "redirect:/couples/${id}"
    }
}