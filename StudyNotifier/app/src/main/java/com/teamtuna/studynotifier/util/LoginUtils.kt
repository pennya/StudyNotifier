package com.teamtuna.studynotifier.util

import com.teamtuna.studynotifier.ui.data.model.LoggedInMember

fun isLoggedIn() = LoggedInMember.member?.id != null

fun getLoggedInMemberId() = LoggedInMember.member?.id
