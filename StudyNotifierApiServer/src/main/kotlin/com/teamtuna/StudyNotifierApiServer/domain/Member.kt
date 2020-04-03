package com.teamtuna.StudyNotifierApiServer.domain

import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Member(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,
        var email: String,
        var pw: String,
        var name: String,
        var profileUrl: String
)