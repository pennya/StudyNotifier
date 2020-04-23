package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudyService {

    @Autowired
    lateinit var studyRepository: StudyRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    // 스터디 추가
    fun addStudy(study: Study): ApiResult<Study> {
        return ApiResult(studyRepository.save(study))
    }

    // 특정 회원의 스터디 찾기
    fun findStudy(memberId: Long): ApiResult<List<Study>> {
        memberRepository.findById(memberId).orElse(null)?.let {
            return ApiResult(studyRepository.findStudyByMember(it))
        }

        return ApiResult(error = ApiError(
                apiErrorType = ApiErrorType.MESSAGE,
                apiErrorCode = ApiErrorCode.NOT_FOUND))
    }

    // 스터디 수정
    fun updateStudy() {
        // TODO  앱에서 스터디 기록 수정 요구사항이 생기면 작업
    }

    // 스터디 삭제
    fun deleteStudy(id: Long) {
        studyRepository.deleteById(id)
    }
}