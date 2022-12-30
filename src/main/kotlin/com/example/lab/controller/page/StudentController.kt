package com.example.lab.controller.page

import com.example.lab.model.Couple
import com.example.lab.model.dto.request.CoupleDto
import com.example.lab.model.dto.request.toDTO
import com.example.lab.model.dto.response.BaseCoupleResponses
import com.example.lab.model.enums.Period
import com.example.lab.service.*
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.*


//        val currentCalendar = Calendar.getInstance()
//        val week = currentCalendar[Calendar.WEEK_OF_YEAR]
//        val year = currentCalendar[Calendar.YEAR]
//        val targetCalendar = Calendar.getInstance()
//        targetCalendar.time = n
//        val targetWeek = targetCalendar[Calendar.WEEK_OF_YEAR]
//        val targetYear = targetCalendar[Calendar.YEAR]
//        //week == targetWeek && year == targetYear
@Controller
class StudentController(
    private val userService: UserService,
    private val studentService: StudentService,
    private val groupService: GroupService,
    private val coupleService: CoupleService,
    private val occupationService: OccupationService
) {

    @PostMapping("/students")
    fun studentCreate(@RequestParam userId: UUID): String {
        studentService.createStudent(userId)
        return "redirect:/users/${userId}"
    }

    @GetMapping("/student/my/couple")
    fun userCouple(model: Model): String {
        val currentUser = userService.findCurrentUser()
        userService.saveUser(currentUser)
        val coupleResponse = BaseCoupleResponses()
        val student = studentService.findStudentById(currentUser.student!!.id!!)
        return "redirect:/groups/${student.group!!.id}/couple"
    }

    @GetMapping("/groups/{groupId}/couple")
    fun load(@PathVariable groupId: Long, model: Model): String {
        val now = LocalDate.now()
        val weak = WeekFields.of(Locale.getDefault())
        val number = now.get(weak.weekOfWeekBasedYear());
        val firstDay = now.with(DayOfWeek.MONDAY)
        val isNum = number % 2 == 0
        val coupleResponse = BaseCoupleResponses(group = groupId)

        val group = groupService.findById(groupId)
        group.couple.forEach {
            val day = now.with(it.day)
            if (isNum && it.period == Period.NUMERATOR || !isNum && it.period == Period.DENOMINATION || Period.FULL == it.period) {
                val list = loadDay(it.day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }
        group.occupation.forEach {
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
        val t = group.couple.firstOrNull()
        if (t == null) {
            model.addAttribute("dto", BaseCoupleResponses())
            return "base_couple"
        }
        t.semester.holidays.forEach {
            if (it.date.get(weak.weekOfWeekBasedYear()) == number) {
                nullDay(it.date.dayOfWeek, coupleResponse)
            }
        }
        model.addAttribute("dto", coupleResponse)
        return "base_couple"
    }

    @GetMapping("/groups/{groupId}/couple/{today}")
    fun load(
        @PathVariable groupId: Long,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) today: LocalDate,
        model: Model
    ): String {
        val now = today
        val weak = WeekFields.of(Locale.getDefault())
        val number = now.get(weak.weekOfWeekBasedYear());
        val firstDay = now.with(DayOfWeek.MONDAY)
        val isNum = number % 2 == 0
        val coupleResponse = BaseCoupleResponses(group = groupId, hidenToday = now)

        val group = groupService.findById(groupId)
        group.couple.forEach {
            val day = now.with(it.day)
            if (isNum && it.period == Period.NUMERATOR || !isNum && it.period == Period.DENOMINATION || Period.FULL == it.period) {
                val list = loadDay(it.day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }
        group.occupation.forEach {
            val weak1 = it.date.get(weak.weekOfWeekBasedYear())
            if (weak1 == number) {
                val day = now.dayOfWeek
                val list = loadDay(day, coupleResponse)
                list?.put(it.hour, it.toDTO())
            }
        }

        val t = group.couple.firstOrNull()
        if (now == LocalDate.now()) {
            coupleResponse.today = now.dayOfWeek
        }

        if (t == null) {
            model.addAttribute("dto", BaseCoupleResponses(group = groupId))
            return "base_couple"
        }
        t.semester.holidays.forEach {
            if (it.date.get(weak.weekOfWeekBasedYear()) == number) {
                nullDay(it.date.dayOfWeek, coupleResponse)
            }
        }
        model.addAttribute("dto", coupleResponse)
        return "base_couple"
    }

    @GetMapping("/aud/{aud}/couple")
    fun loadAud(@PathVariable aud: String, model: Model): String {
        val now = LocalDate.now()
        val weak = WeekFields.of(Locale.getDefault())
        val number = now.get(weak.weekOfWeekBasedYear());
        val firstDay = now.with(DayOfWeek.MONDAY)
        val isNum = number % 2 == 0
        val coupleResponse = BaseCoupleResponses(aud = aud)

        val couples = coupleService.loadAllByAud(aud)
//        couples.forEach {
//            val day = now.with(it.day)
//            if (isNum && it.period == Period.NUMERATOR || !isNum && it.period == Period.DENOMINATION || Period.FULL == it.period) {
//                val list = loadDay(it.day, coupleResponse)
//                list?.put(it.hour, it.toDTO())
//            }
//        }
        val ocup = occupationService.loadAllByAud(aud)

        ocup.forEach {
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
        model.addAttribute("dto", coupleResponse)
        return "aud_teacher_coupless"
    }

    @GetMapping("/aud/{aud}/couple/{today}")
    fun loadAud(
        @PathVariable aud: String,
        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) today: LocalDate,
        model: Model
    ): String {
        val now = today
        val weak = WeekFields.of(Locale.getDefault())
        val number = now.get(weak.weekOfWeekBasedYear());
        val firstDay = now.with(DayOfWeek.MONDAY)
        val isNum = number % 2 == 0
        val coupleResponse = BaseCoupleResponses(aud = aud, hidenToday = now)

//        val couples = coupleService.loadAllByAud(aud)
//        couples.forEach {
//            val day = now.with(it.day)
//            if (isNum && it.period == Period.NUMERATOR || !isNum && it.period == Period.DENOMINATION || Period.FULL == it.period) {
//                val list = loadDay(it.day, coupleResponse)
//                list?.put(it.hour, it.toDTO())
//            }
//        }
        val ocup = occupationService.loadAllByAud(aud)

        ocup.forEach {
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
        model.addAttribute("dto", coupleResponse)
        return "aud_teacher_coupless"
    }

    private fun nullDay(dayOfWeek: DayOfWeek, coupleResponse: BaseCoupleResponses) {
        when (dayOfWeek) {
            DayOfWeek.MONDAY -> coupleResponse.mon = null
            DayOfWeek.TUESDAY -> coupleResponse.tue = null
            DayOfWeek.THURSDAY -> coupleResponse.thu = null
            DayOfWeek.WEDNESDAY -> coupleResponse.wen = null
            DayOfWeek.FRIDAY -> coupleResponse.fri = null
            DayOfWeek.SATURDAY -> coupleResponse.sun = null
        }
    }

    private fun loadDay(dayOfWeek: DayOfWeek, coupleResponse: BaseCoupleResponses): MutableMap<Int, CoupleDto>? {
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