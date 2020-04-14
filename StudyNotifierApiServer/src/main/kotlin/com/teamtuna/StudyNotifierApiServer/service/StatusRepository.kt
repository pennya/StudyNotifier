package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Status
import org.springframework.data.jpa.repository.JpaRepository

interface StatusRepository: JpaRepository<Status, Long> {
}