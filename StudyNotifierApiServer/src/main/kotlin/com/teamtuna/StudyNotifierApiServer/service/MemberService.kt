package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    fun addMember(member: Member): Member {
        return memberRepository.save(member)
    }

    fun findById(id: Long): Optional<Member> {
        return memberRepository.findById(id)
    }

    fun findByEmail(email: String): Optional<Member> {
        return memberRepository.findByEmail(email)
    }
}