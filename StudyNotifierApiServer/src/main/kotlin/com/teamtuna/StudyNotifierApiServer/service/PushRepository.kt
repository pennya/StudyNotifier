package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Push
import org.springframework.data.jpa.repository.JpaRepository

interface PushRepository: JpaRepository<Push, Long> {
    fun findByIsSendFalse(): List<Push>
}