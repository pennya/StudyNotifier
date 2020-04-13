package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Study
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudyService {

    @Autowired
    lateinit var studyRepository: StudyRepository

    // 스터디 추가
    fun addStudy(study: Study): Study {
        return studyRepository.save(study)
    }

    // 스터디 수정
    fun updateStudy() {

    }

    // 스터디 삭제
    fun deleteStudy(id: Long) {
        studyRepository.deleteById(id)
    }
}