package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.service.MemberService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap

@RunWith(SpringRunner::class)
@SpringBootTest
internal class StatusControllerTest {

    @Autowired
    lateinit var statusController: StatusController

    @Autowired
    lateinit var memberService: MemberService

    var mockMvc: MockMvc? = null

    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(statusController).build()
    }

    @Test
    fun `GET 테스트`() {
        mockMvc?.perform(MockMvcRequestBuilders.get("/rest/v1/simple"))
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andReturn()
    }

    @Test
    fun `회원 상태 테이블 조회 및 생성 테스트`() {
        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        val addedMember = memberService.addMember(member)

        val requestParams = LinkedMultiValueMap<String, String>()
        requestParams.add("memberId", addedMember.id.toString())

        mockMvc?.perform(MockMvcRequestBuilders.get("/rest/v1/status")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .params(requestParams))
                ?.andDo(MockMvcResultHandlers.print())
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andReturn()
    }
}