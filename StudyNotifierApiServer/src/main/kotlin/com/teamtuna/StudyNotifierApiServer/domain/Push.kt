package com.teamtuna.StudyNotifierApiServer.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Push(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
        var memberId: Long,
        var message: String
)