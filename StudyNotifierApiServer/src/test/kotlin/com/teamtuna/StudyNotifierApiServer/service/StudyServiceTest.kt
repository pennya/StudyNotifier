package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.domain.Study
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
internal class StudyServiceTest {

    @Autowired
    lateinit var studyRepository: StudyRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun `스터디 생성 & 확인`() {
        val member = Member(email = "aaaa", pw = "2222", name = "name")
        val member2 = Member(email = "bbbb", pw = "2222", name = "wwww")
        val savedMember = memberRepository.save(member)
        val savedMember2 = memberRepository.save(member2)

        val study = Study(
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 3000,
                date = System.currentTimeMillis(),
                title = "title1",
                description = "description1",
                member = member)

        studyRepository.save(study)

        val study2 = Study(
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 3000,
                date = System.currentTimeMillis(),
                title = "title2",
                description = "description2",
                member = member)

        studyRepository.save(study2)

        val study3 = Study(
                startTime = System.currentTimeMillis(),
                endTime = System.currentTimeMillis() + 3000,
                date = System.currentTimeMillis(),
                title = "title3",
                description = "description3",
                member = member2)

        studyRepository.save(study3)

        savedMember.id?.let {
            val loadedMember = memberRepository.findById(it)
            val studyList = loadedMember.get().study
            println("studyList size ${studyList.size}")
            for (_study in studyList) {
                Assertions.assertThat(_study.title).isEqualTo(study.title)
                break
            }
        }

        val findStudyByMember = studyRepository.findStudyByMember(savedMember2)
        println("findStudyByMember size ${findStudyByMember.size}")
        for (_study in findStudyByMember) {
            Assertions.assertThat(_study.title).isEqualTo(study3.title)
        }
    }
}
