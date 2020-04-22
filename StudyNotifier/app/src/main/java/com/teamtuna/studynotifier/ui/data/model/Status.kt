package com.teamtuna.studynotifier.ui.data.model

data class Status(
        var id: Long? = null,
        var totalStudyTime: Long,
        var lastUpdatedTime: Long,
        var grade: String = "",
        var member: Member
)