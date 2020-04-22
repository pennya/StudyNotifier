package com.teamtuna.studynotifier.ui.data.model

data class Study(
        var id: Long? = null,
        var startTime: Long,
        var endTime: Long,
        var date: Long,
        var title: String,
        var description: String = "",
        var member: Member? = null
)