package com.example.lab.service

import com.example.lab.exceptions.NotFoundException
import com.example.lab.model.Occupation
import com.example.lab.model.dto.request.OccupationCreateRequests
import com.example.lab.repository.OccupationRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

@Service
class OccupationService(
    private val occupationRepository: OccupationRepository,
    private val userService: UserService,
    private val groupService: GroupService
) {

    fun createOccupation(occupationCreateRequests: OccupationCreateRequests): Long {
        val curUser = userService.findCurrentUser()
//        if (occupationCreateRequests.date.dayOfWeek == DayOfWeek.SUNDAY) {
//            throw UnsupportedOperationException("Can not create ocup in sunday")
//        }
        val newOc = Occupation(
            hour = occupationCreateRequests.hour,
            date = LocalDate.parse(occupationCreateRequests.date),
            teacher = curUser.teacher!!,
            audience = occupationCreateRequests.audience!!,
            subject = occupationCreateRequests.subjectName!!
        )
        check(LocalDate.parse(occupationCreateRequests.date), occupationCreateRequests.hour, curUser.teacher!!.id)
        return occupationRepository.save(newOc).id!!
    }

    fun updateOccupation(ocupId: Long, occupationCreateRequests: OccupationCreateRequests) {
//        if (occupationCreateRequests.date.dayOfWeek == DayOfWeek.SUNDAY) {
//            throw UnsupportedOperationException("Can not create ocup in sunday")
//        }
        val ocu = getOcup(ocupId)
        val date = LocalDate.parse(occupationCreateRequests.date)
        if (ocu.date != date || ocu.hour != occupationCreateRequests.hour)
            check(LocalDate.parse(occupationCreateRequests.date), occupationCreateRequests.hour, ocu.teacher!!.id)

        ocu.hour = occupationCreateRequests.hour
        ocu.date = LocalDate.parse(occupationCreateRequests.date)
        ocu.audience = occupationCreateRequests.audience!!
        ocu.subject = occupationCreateRequests.subjectName!!
        occupationRepository.save(ocu)
    }

    fun loadAllByAud(aud: String): List<Occupation> {
        return occupationRepository.findAllByAudience(aud)
    }

    fun check(date: LocalDate, hour: Int, teacherId: UUID) {
        if (occupationRepository.findAllByDateAndHourAndTeacherId(date, hour, teacherId).isNotEmpty()) {
            throw NotFoundException("re")
        }
    }

    fun deleteCouple(coupleId: Long) {
        occupationRepository.deleteById(coupleId)
    }

    fun addGroup(coupleId: Long, groupId: Long) {
        val couple = getOcup(coupleId)
        val group = groupService.findById(groupId)
        couple.groups.add(group)
        occupationRepository.save(couple)
    }

    fun removeGroup(coupleId: Long, groupId: Long) {
        val couple = getOcup(coupleId)
        val group = groupService.findById(groupId)
        couple.groups.remove(group)
        occupationRepository.save(couple)
    }

    fun loadTeacher(id: UUID): List<Occupation> {
        return occupationRepository.findAllByTeacherIdOrderByDateAscHour(id)
    }

    fun getOcup(id: Long): Occupation {
        return occupationRepository.findById(id).orElseThrow {
            NotFoundException("not found")
        }
    }
}