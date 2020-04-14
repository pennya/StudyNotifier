package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Member
import com.teamtuna.StudyNotifierApiServer.domain.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StatusService {

    @Autowired
    lateinit var statusRepository: StatusRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    // 회원 id 에 해당하는 status 있는지 조회
    fun findOneById(memberId: Long): Status? {
        println("StatusService.findOneById")
        val member: Member? = memberRepository.findById(memberId).orElse(null) ?: return null
        return member?.let {
            statusRepository.findAll().find { it.member.id == member.id }
        }
    }

    // status 생성
    fun addStatus(status: Status): Status {
        return statusRepository.save(status)
    }

    // status 수정
    fun updateStatus(status: Status) {

    }

    // status 삭제
    fun deleteStatus(id: Long) {
        statusRepository.deleteById(id)
    }
}