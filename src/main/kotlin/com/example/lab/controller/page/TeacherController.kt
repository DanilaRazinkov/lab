package com.example.lab.controller.page

import com.example.lab.exceptions.NotFoundException
import com.example.lab.model.dto.request.*
import com.example.lab.model.dto.response.BaseCoupleResponses
import com.example.lab.model.dto.response.TeacherCoupleResponses
import com.example.lab.model.enums.Period
import com.example.lab.service.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*

@Controller
class TeacherController(
    private val teacherService: TeacherService,
    private val userService: UserService,
    private val coupleService: CoupleService,
    private val occupationService: OccupationService,
    private val groupService: GroupService
) {

    @PostMapping("/teachers/{userId}/update")
    fun updateTeacher(
        @PathVariable userId: UUID,
        @ModelAttribute("teacher") teacherCreateRequests: TeacherCreateRequests
    ): String {
        teacherService.updateTeacher(userId, teacherCreateRequests)
        return "redirect:/users/${userId}"
    }

    @GetMapping("/ocups")
    @PreAuthorize("hasRole('TEACHER')")
    fun ocupCreatPage(
        @RequestParam(name = "error", defaultValue = "") error: String,
        model: Model
    ): String {
        model.addAttribute("dto", OccupationCreateRequests())
        model.addAttribute("error", error)
        return "occouple-create-page"
    }

    @GetMapping("/ocups/my")
    @PreAuthorize("hasRole('TEACHER')")
    fun ocupCeatPage(
        model: Model
    ): String {
        val curUse = userService.findCurrentUser()
        model.addAttribute("ocu", occupationService.loadTeacher(curUse.teacher!!.id))
        return "ocouple-pages"
    }

    @GetMapping("/ocups/{coupleId}")
    @PreAuthorize("hasRole('TEACHER')")
    fun loadCreatCoupleByIdPage(model: Model, @PathVariable coupleId: Long): String {
        val couple = occupationService.getOcup(coupleId)
        model.addAttribute(
            "dto", OccupationCreateRequests(
                hour = couple.hour,
                subjectName = couple.subject,
                audience = couple.audience,
                date = couple.date.toString()
            )
        )
        model.addAttribute(
            "ocu", couple
        )
        model.addAttribute("groups", groupService.findAll().distinct())
        model.addAttribute("id", coupleId)
        return "ocouple-page"
    }

    @PostMapping("/ocups")
    fun createOcup(
        @ModelAttribute("teacher") occupationCreateRequests: OccupationCreateRequests
    ): String {
        try {
            val id = occupationService.createOccupation(occupationCreateRequests)
            return "redirect:/ocups/${id}"
        } catch (e: NotFoundException) {
            return "redirect:/ocups?error=Time reserved"
        }
    }

    @PostMapping("/ocups/{coupleId}")
    @PreAuthorize("hasRole('TEACHER')")
    fun updateCreatCoupleByIdPage(
        @ModelAttribute("dto") dto: OccupationCreateRequests,
        @PathVariable coupleId: Long
    ): String {
        try {
            occupationService.updateOccupation(coupleId, dto)
            return "redirect:/ocups/${coupleId}"
        } catch (e: NotFoundException) {
            return "redirect:/ocups?error=Time reserved"
        }
    }

    @PostMapping("/ocups/{coupleId}/groups/{groupId}")
    @PreAuthorize("hasRole('TEACHER')")
    fun addGroup(
        @PathVariable coupleId: Long, @PathVariable groupId: Long
    ): String {
        occupationService.addGroup(coupleId, groupId)
        return "redirect:/ocups/${coupleId}"
    }

    @PostMapping("/ocups/{coupleId}/groups/{groupId}/delete")
    @PreAuthorize("hasRole('TEACHER')")
    fun removeGroup(
        @PathVariable coupleId: Long, @PathVariable groupId: Long
    ): String {
        occupationService.removeGroup(coupleId, groupId)
        return "redirect:/ocups/${coupleId}"
    }

    @PostMapping("/ocups/{coupleId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateCreatCoupleByIdPage(
        @PathVariable coupleId: Long
    ): String {
        occupationService.deleteCouple(coupleId)
        return "redirect:/index"
    }

    @GetMapping("/teacher/my/couple")
    fun userCouple(model: Model): String {
        val currentUser = userService.findCurrentUser()
        userService.saveUser(currentUser)
        val now = LocalDate.now()
        val weak = WeekFields.of(Locale.getDefault())
        val number = now.get(weak.weekOfWeekBasedYear());
        val firstDay = now.with(DayOfWeek.MONDAY)
        val isNum = number % 2 == 0
        val coupleResponse = TeacherCoupleResponses()
        val teacher = teacherService.findById(currentUser.teacher!!.id)
        teacher.couple.forEach {
            val day = now.with(it.day)
            if (isNum && it.period == Period.NUMERATOR || !isNum && it.period == Period.DENOMINATION || Period.FULL == it.period) {
                val list = loadDay(it.day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }
        teacher.occupation.forEach {
            val weak1 = it.date.get(weak.weekOfWeekBasedYear())
            if (weak1 == number) {
                val day = now.dayOfWeek
                val list = loadDay(day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }
        if (now == LocalDate.now()) {
            coupleResponse.today = now.dayOfWeek
        }
//        val t = currentUser.teacher!!.couple.firstOrNull()
//        if (t == null) {
//            model.addAttribute("dto", BaseCoupleResponses())
//            return "base_couple"
//        }
//        t.semester.holidays.forEach {
//            if (it.date.get(weak.weekOfWeekBasedYear()) == number) {
//                nullDay(it.date.dayOfWeek, coupleResponse)
//            }
//        }
        model.addAttribute("dto", coupleResponse)
        return "teacher_coupless"
    }

    @GetMapping("/teacher/my/{today}")
    fun load(
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) today: LocalDate,
        model: Model
    ): String {
        val now = today
        val weak = WeekFields.of(Locale.getDefault())
        val number = now.get(weak.weekOfWeekBasedYear());
        val firstDay = now.with(DayOfWeek.MONDAY)
        val isNum = number % 2 == 0
        val coupleResponse = TeacherCoupleResponses(hidenToday = now)

        val currentUser = userService.findCurrentUser()
        val teacher = teacherService.findById(currentUser.teacher!!.id)
        teacher.couple.forEach {
            val day = now.with(it.day)
            if (isNum && it.period == Period.NUMERATOR || !isNum && it.period == Period.DENOMINATION || Period.FULL == it.period) {
                val list = loadDay(it.day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }
        teacher.occupation.forEach {
            val weak1 = it.date.get(weak.weekOfWeekBasedYear())
            if (weak1 == number) {
                val day = now.dayOfWeek
                val list = loadDay(day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }
//        val t = currentUser.teacher!!.couple.firstOrNull()
//        if (t == null) {
//            model.addAttribute("dto", TeacherCoupleResponses())
//            return "base_couple"
//        }
//        t.semester.holidays.forEach {
//            if (it.date.get(weak.weekOfWeekBasedYear()) == number) {
//                nullDay(it.date.dayOfWeek, coupleResponse)
//            }
//        }
        model.addAttribute("dto", coupleResponse)
        return "teacher_coupless"
    }

    private fun nullDay(dayOfWeek: DayOfWeek, coupleResponse: TeacherCoupleResponses) {
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> coupleResponse.mon = null
            DayOfWeek.TUESDAY -> coupleResponse.tue = null
            DayOfWeek.THURSDAY -> coupleResponse.thu = null
            DayOfWeek.WEDNESDAY -> coupleResponse.wen = null
            DayOfWeek.FRIDAY -> coupleResponse.fri = null
            DayOfWeek.SATURDAY -> coupleResponse.sun = null
        }
    }

    private fun loadDay(dayOfWeek: DayOfWeek, coupleResponse: TeacherCoupleResponses): MutableMap<Int, CoupleDto>? {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> coupleResponse.mon
            DayOfWeek.TUESDAY -> coupleResponse.tue
            DayOfWeek.THURSDAY -> coupleResponse.thu
            DayOfWeek.WEDNESDAY -> coupleResponse.wen
            DayOfWeek.FRIDAY -> coupleResponse.fri
            DayOfWeek.SATURDAY -> coupleResponse.sun
            else -> mutableMapOf()
        }
    }

}