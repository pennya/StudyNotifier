package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.domain.Status
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
internal class StatusServiceTest {

    @Autowired
    lateinit var statusService: StatusService

    @Autowired
    lateinit var memberService: MemberService

    @Test
    fun `상태 테이블 조회 후 생성`() {
        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        val addedMember = memberService.addMember(member)
        var status: Status? = addedMember.id?.let { statusService.findOneById(it) }
        if (status == null) {
            status = Status(
                    totalStudyTime = System.currentTimeMillis(),
                    lastUpdatedTime = System.currentTimeMillis(),
                    member = member
            )
            val addedStatus = statusService.addStatus(status)

            val status2 = addedMember.id?.let { statusService.findOneById(it) }
            assertThat(status2?.member?.id).isEqualTo(addedStatus.member.id)
        }
    }
}