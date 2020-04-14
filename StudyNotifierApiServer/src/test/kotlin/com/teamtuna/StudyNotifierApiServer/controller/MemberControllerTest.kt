package com.teamtuna.StudyNotifierApiServer.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.teamtuna.StudyNotifierApiServer.domain.Member
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringRunner::class)
@SpringBootTest
internal class MemberControllerTest {

    @Autowired
    lateinit var memberController: MemberController

    var mockMvc: MockMvc? = null

    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build()
    }

    @Test
    fun `회원가입 테스트`() {
        val mapper = ObjectMapper()

        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        mockMvc?.perform(post("/rest/v1/member")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mapper.writeValueAsString(member)))
                ?.andExpect(status().isOk)
                ?.andDo(print())
                ?.andReturn()
    }
}