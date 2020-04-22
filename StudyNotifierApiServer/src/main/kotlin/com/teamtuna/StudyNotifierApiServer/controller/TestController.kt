package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.domain.ApiResult
import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @Autowired
    lateinit var memberService: MemberService

    @GetMapping("/rest/v1/test")
    fun getData(): String {
        return "test"
    }

    @GetMapping("/rest/v1/test2")
    fun getApiResultData(): ApiResult<String> {
        return ApiResult("test")
    }

    @GetMapping("/rest/v1/test3")
    fun getApiResultMemberData(): ApiResult<Member> {
        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        val addedMember = memberService.addMember(member)
        println(addedMember)
        return addedMember
    }
}