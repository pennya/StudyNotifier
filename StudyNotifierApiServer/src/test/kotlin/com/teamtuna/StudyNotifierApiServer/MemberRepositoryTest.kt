package com.teamtuna.StudyNotifierApiServer

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.service.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.sql.DataSource

@RunWith(SpringRunner::class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun `멤버 추가 테스트`() {
        dataSource.connection.use { connection ->
            val metaData = connection.metaData
            println(metaData.url)
            println(metaData.driverName)
            println(metaData.userName)
        }

        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        val newMember: Member = memberRepository.save(member)

        assertThat(newMember).isNotNull
        val existingMember: Optional<Member> = memberRepository.findByEmail(newMember.email)
        assertThat(existingMember).isNotNull

        val nonExistingMember: Optional<Member> = memberRepository.findByEmail("testUserName")
        assertThat(nonExistingMember).isNotNull

    }
}