package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.domain.Status
import com.teamtuna.StudyNotifierApiServer.service.MemberService
import com.teamtuna.StudyNotifierApiServer.service.StatusService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class StatusController {

    @Autowired
    lateinit var statusService: StatusService

    @Autowired
    lateinit var memberService: MemberService

    @RequestMapping(value = ["/rest/v1/simple"], method = [RequestMethod.GET])
    fun simpleTest(): String {
        return "GET SIMPLE TEST SUCCESS"
    }

    @ResponseBody
    @RequestMapping(value = ["/rest/v1/status"], method = [RequestMethod.GET])
    fun findAndAddStatus(@RequestParam memberId: Long): Status {
        val status = statusService.findOneById(memberId) ?: statusService.addStatus(Status(
                totalStudyTime = 0,
                lastUpdatedTime = System.currentTimeMillis(),
                grade = "브론즈",
                member = memberService.findOne(memberId).data!!
        ))

        println(status)
        return status
    }
}