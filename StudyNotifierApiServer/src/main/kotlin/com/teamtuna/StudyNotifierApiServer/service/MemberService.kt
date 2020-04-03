package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    // 회원 조회
    fun findOne(id: Long): Member {
        return memberRepository.getOne(id)
    }

    // 회원 전체 조회
    fun findAllMembers(): List<Member> {
        return memberRepository.findAll()
    }

    // 회원 추가
    fun addMember(member: Member): Member {
        return memberRepository.save(member)
    }

    // 회원 삭제
    fun deleteMember(id: Long) {
        memberRepository.deleteById(id)
    }

    // 회원 업데이트
    fun updateMember(id: Long, member: Member): Member {
        val tempMember = memberRepository.findById(id).get()
        tempMember.email = member.email
        tempMember.name = member.name
        tempMember.pw = member.pw
        tempMember.profileUrl = member.profileUrl

        return memberRepository.save(tempMember)
    }

    // 회원 찾기 (id)
    fun findById(id: Long): Optional<Member> {
        return memberRepository.findById(id)
    }

    // 회원 찾기 (email)
    fun findByEmail(email: String): Optional<Member> {
        return memberRepository.findByEmail(email)
    }
}