package com.teamtuna.StudyNotifierApiServer

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.domain.Study
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

    @Test
    fun `전체 멤버 조회`() {
        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        val member2 = Member(
                email = "asdf",
                pw = "1234",
                name = "Park",
                profileUrl = "testUrl2"
        )

        memberRepository.save(member)
        memberRepository.save(member2)

        for ((index,_member) in memberRepository.findAll().withIndex()) {
            assertThat(_member).isNotNull
            print(_member)
        }
    }

    @Test
    fun `멤버 정보 업데이트`() {
        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        memberRepository.save(member)

        val tempMember = memberRepository.findByEmail("rlawlgns077@naver").get()
        tempMember.name = "Park"
        memberRepository.save(tempMember)


        val tempMember2 = memberRepository.findByEmail("rlawlgns077@naver").get()
        assertThat(tempMember2.name).isEqualTo("Park")
    }

    @Test
    fun `스터디 생성 및 조회`() {
        val member = Member(
                email = "rlawlgns077@naver",
                pw = "1234",
                name = "kim",
                profileUrl = "testUrl"
        )

        memberRepository.save(member)

        val study1 = Study(
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 100000,
                date = System.currentTimeMillis(),
                title = "스터디1",
                description = "스터디 설명1",
                member = member
        )

        member.addStudy(study1)

        val study2 = Study(
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 100000,
                date = System.currentTimeMillis(),
                title = "스터디2",
                description = "스터디 설명2",
                member = member
        )

        member.addStudy(study2)

        val tempMember = memberRepository.findByEmail("rlawlgns077@naver").get()
        assertThat(tempMember.study[0].title).isEqualTo("스터디1")
        assertThat(tempMember.study[1].title).isEqualTo("스터디2")
    }
}