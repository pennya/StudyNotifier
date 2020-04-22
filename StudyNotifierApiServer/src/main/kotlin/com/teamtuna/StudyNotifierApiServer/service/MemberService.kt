package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberService {

    @Autowired
    private lateinit var memberRepository: MemberRepository

    // 회원 조회
    fun findOne(id: Long): ApiResult<Member> {
        return ApiResult(memberRepository.getOne(id))
    }

    // 회원 전체 조회
    fun findAllMembers(): ApiResult<List<Member>> {
        return ApiResult(memberRepository.findAll())
    }

    // 회원 추가
    fun addMember(member: Member): ApiResult<Member> {
        val foundMember: Optional<Member> = memberRepository.findByEmail(member.email)
        if (foundMember.isPresent) {
            return ApiResult(error = ApiError(apiErrorType = ApiErrorType.MESSAGE,
                    apiErrorCode = ApiErrorCode.DUPLICATE_LOGIN_ID))
        }

        // TODO  Spring Security BCrypt 사용
        return ApiResult(memberRepository.save(member))
    }

    // 회원 삭제
    fun deleteMember(id: Long) {
        memberRepository.deleteById(id)
    }

    // 회원 업데이트
    fun updateMember(id: Long, member: Member): ApiResult<Member> {
        val tempMember = memberRepository.findById(id).get()

        tempMember.email = member.email
        tempMember.name = member.name
        tempMember.pw = member.pw
        tempMember.deviceToken = member.deviceToken
        tempMember.profileUrl = member.profileUrl

        return ApiResult(memberRepository.save(tempMember))
    }

    // 회원 찾기 (id)
    fun findById(id: Long): ApiResult<Member> {
        return try {
            return ApiResult(memberRepository.findById(id).get())
        } catch (e: Exception) {
            ApiResult(error = ApiError(e))
        }
    }

    // 회원 찾기 (email)
    fun findByEmail(email: String): ApiResult<Member> {
        return try {
            return ApiResult(memberRepository.findByEmail(email).get())
        } catch (e: Exception) {
            ApiResult(error = ApiError(e))
        }
    }

    // 특정 회원의 모든 스터디 찾기
    fun findAllStudy(id: Long): ApiResult<MutableList<Study>> {
        return try {
            ApiResult(memberRepository.findById(id).get().study)
        } catch (e: Exception) {
            ApiResult(error = ApiError(e))
        }
    }

    // 로그인
    fun findByEmailAndPwd(email: String, pwd: String): ApiResult<Member> {
        val member = memberRepository.findByEmail(email)
        // TODO  Spring Security BCrypt 사용
        return if (member.isPresent && member.get().pw == pwd) {
            ApiResult(member.get())
        } else {
            ApiResult(error = ApiError(apiErrorType = ApiErrorType.INVALIDATE_INPUT,
                    apiErrorCode = ApiErrorCode.INCORRECT_LOGIN_ID_AND_PASSWORD))
        }
    }
}