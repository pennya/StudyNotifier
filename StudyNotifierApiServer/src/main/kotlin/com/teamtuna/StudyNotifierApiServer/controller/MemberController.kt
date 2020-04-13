package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.domain.Study
import com.teamtuna.StudyNotifierApiServer.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class MemberController {

    @Autowired
    private lateinit var memberService: MemberService

    // 회원 추가
    @RequestMapping(value = ["/rest/v1/member"], method = [RequestMethod.POST])
    fun addMember(@RequestBody member: Member): Member {
        return memberService.addMember(member)
    }

    // 회원 삭제
    @RequestMapping(value = ["/rest/v1/member/{id}"], method = [RequestMethod.DELETE])
    fun deleteMember(@PathVariable("id") id: Long) {
        return memberService.deleteMember(id)
    }

    // 이메일을 이용한 회원 찾기
    @RequestMapping(value = ["/rest/v1/member"], method = [RequestMethod.GET])
    fun findByEmail(@RequestParam email: String): Optional<Member> {
        return memberService.findByEmail(email)
    }

    // 특정 회원의 모든 스터디 찾기
    @RequestMapping(value = ["/rest/v1/member_study"], method = [RequestMethod.GET])
    fun findStudyById(@RequestParam id: Long): MutableList<Study> {
        return memberService.findAllStudy(id)
    }
}