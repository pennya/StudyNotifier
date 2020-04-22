package com.teamtuna.studynotifier.ui.data.model

data class Member(
        var id: Long? = null,
        var email: String,
        var pw: String,
        var name: String = "",
        var deviceToken: String = "",
        var profileUrl: String = "",
        var study: MutableList<Study> = mutableListOf()
)