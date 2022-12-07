package com.example.lab.controller.page

import com.example.lab.model.dto.request.GroupCreatRequest
import com.example.lab.service.GroupService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
class GroupController(
    private val groupService: GroupService
) {

    @GetMapping("/groups")
    fun groupsPage(model: Model): String {
        model.addAttribute("groups", groupService.findAll())
        model.addAttribute("groupCreateRequest", GroupCreatRequest())
        return "groups"
    }

    @GetMapping("/groups/{groupId}")
    fun groupPageById(model: Model, @PathVariable groupId: Long): String {
        model.addAttribute("group", groupService.findById(groupId))
        return "group-page"
    }

    @PostMapping("/groups")
    fun createGroup(@ModelAttribute("groupCreatRequest") groupCreatRequest: GroupCreatRequest): String {
        groupService.creatGroup(groupCreatRequest)
        return "redirect:/groups"
    }

    @GetMapping("/students/{studentId}/groups")
    fun studentAddGroupInGroupPage(@PathVariable studentId: UUID, model: Model): String {
        model.addAttribute("pageDto", groupService.createStudentAddGroupPage(studentId))
        return "/student-group-page"
    }

    @PostMapping("/students/{studentId}/groups/{groupId}")
    fun addUserInGroup(@PathVariable studentId: UUID, model: Model, @PathVariable groupId: Long): String {
        groupService.addUserInGroup(studentId, groupId)
        return "redirect:/students/${studentId}/groups"
    }

    @PostMapping("/students/{studentId}/groups/{groupId}/delete")
    fun removeUserFormGroup(@PathVariable studentId: UUID, model: Model, @PathVariable groupId: Long, @RequestHeader referer: String): String {
        groupService.removeUserFromGroup(studentId, groupId)
        return "redirect:$referer"
    }

}