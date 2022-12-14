package com.example.lab.service

import com.example.lab.exceptions.NotFoundException
import com.example.lab.model.Group
import com.example.lab.model.dto.request.GroupCreatRequest
import com.example.lab.model.dto.request.StudentAddGroupPage
import com.example.lab.repository.GroupRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val studentService: StudentService
) {

    fun findAll(): List<Group> {
        return groupRepository.findAll()
    }

    fun findById(id: Long): Group {
        val group = groupRepository.findById(id)
            .orElseThrow {
                throw NotFoundException("Group with id not found")
            }
        group.student = group.student.distinct().toMutableList()
        return group
    }

    fun saveGroup(group: Group): Group {
        return groupRepository.save(group)
    }

    fun creatGroup(groupCreatRequest: GroupCreatRequest) {
        groupRepository.save(Group(number = groupCreatRequest.number, course = groupCreatRequest.course))
    }

    fun createStudentAddGroupPage(studentId: UUID): StudentAddGroupPage {
        val student = studentService.findStudentById(studentId)
        val notUserGroups = groupRepository.findAllByStudentNotContaining(student)
        return StudentAddGroupPage(
            student = student,
            notUserGroups = notUserGroups
        )
    }

    fun addUserInGroup(studentId: UUID, groupId: Long) {
        val group = findById(groupId)
        val student = studentService.addStudentInGroup(studentId, group)
        group.student.add(student)
        groupRepository.save(group)
    }

    fun removeUserFromGroup(studentId: UUID, groupId: Long) {
        val student = studentService.removeStudentFromGroup(studentId)
        val group = findById(groupId)
        group.student.remove(student)
        groupRepository.save(group)
    }
}