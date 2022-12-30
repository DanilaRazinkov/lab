package com.example.lab.service

import com.example.lab.exceptions.NotFoundException
import com.example.lab.model.Couple
import com.example.lab.model.dto.request.CoupleCreatRequests
import com.example.lab.repository.CoupleRepository
import org.springframework.stereotype.Service

@Service
class CoupleService(
    private val coupleRepository: CoupleRepository,
    private val subjectService: SubjectService,
    private val teacherService: TeacherService,
    private val semesterService: SemesterService,
    private val groupService: GroupService
) {
    fun createCouple(coupleCreatRequests: CoupleCreatRequests): String {
        val aud = coupleCreatRequests.audience
        val hour = coupleCreatRequests.hour
        val teacher = teacherService.findById(coupleCreatRequests.teacherId!!)
        val day = coupleCreatRequests.dayOfWeek
        val newCouple = Couple(
            hour = coupleCreatRequests.hour,
            period = coupleCreatRequests.period,
            subject = subjectService.loadOrCreatByName(coupleCreatRequests.subjectName!!),
            teacher = teacher,
            semester = semesterService.findById(coupleCreatRequests.semesterId!!),
            audience = aud!!,
            day = coupleCreatRequests.dayOfWeek
        )
        return coupleRepository.save(newCouple).id.toString()
    }

    fun loadById(coupleId: Long): Couple {
        return coupleRepository.findById(coupleId).orElseThrow { NotFoundException("") }
    }

    fun loadAllByAud(aud: String): List<Couple> {
        return coupleRepository.findAllByAudience(aud)
    }

    fun updateCouple(coupleId: Long, dto: CoupleCreatRequests) {
        val aud = dto.audience
        val hour = dto.hour
        val teacher = teacherService.findById(dto.teacherId!!)
        val day = dto.dayOfWeek
        val couple = loadById(coupleId)
        couple.hour = dto.hour
        couple.period = dto.period
        couple.subject = subjectService.loadOrCreatByName(dto.subjectName!!)
        couple.teacher = teacher
        couple.semester = semesterService.findById(dto.semesterId!!)
        couple.audience = aud!!
        couple.day = dto.dayOfWeek
        coupleRepository.save(couple)
    }

    fun deleteCouple(coupleId: Long) {
        coupleRepository.deleteById(coupleId)
    }

    fun addGroup(coupleId: Long, groupId: Long) {
        val couple = loadById(coupleId)
        val group = groupService.findById(groupId)
        couple.groups.add(group)
        coupleRepository.save(couple)
    }

    fun removeGroup(coupleId: Long, groupId: Long) {
        val couple = loadById(coupleId)
        val group = groupService.findById(groupId)
        couple.groups.remove(group)
        coupleRepository.save(couple)
    }

}