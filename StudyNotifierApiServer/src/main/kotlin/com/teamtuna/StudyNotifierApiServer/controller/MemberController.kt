package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Controller
class MemberController {

    @Autowired
    private lateinit var memberService: MemberService

    @RequestMapping(value = ["/member"], method = [RequestMethod.POST])
    fun addMember(@RequestBody member: Member): Member {
        return memberService.addMember(member)
    }

    @RequestMapping(value = ["/member"], method = [RequestMethod.GET])
    fun findByEmail(@RequestParam email: String): Optional<Member> {
        return memberService.findByEmail(email)
    }
}