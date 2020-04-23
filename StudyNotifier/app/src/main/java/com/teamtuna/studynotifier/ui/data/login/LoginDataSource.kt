package com.teamtuna.studynotifier.ui.data.login

import com.teamtuna.studynotifier.service.RetrofitService
import com.teamtuna.studynotifier.ui.data.UiResult
import com.teamtuna.studynotifier.ui.data.model.ApiErrorCode.INCORRECT_LOGIN_ID_AND_PASSWORD
import com.teamtuna.studynotifier.ui.data.model.Member
import com.teamtuna.studynotifier.util.PP
import java.io.IOException

class LoginDataSource {

    suspend fun login(email: String, pwd: String): UiResult<Member> {
        try {
            var member = RetrofitService.login(email, pwd)
            member.error?.let {
                when (it.apiErrorCode) {
                    INCORRECT_LOGIN_ID_AND_PASSWORD -> {
                        member = RetrofitService.signUp(
                                Member(
                                        email = email,
                                        pw = pwd,
                                        deviceToken = PP.PUSH_TOKEN.getString("") ?: ""
                                )
                        )
                    }
                    else -> {}
                }
            }

            return member.data?.let {
                UiResult.Success(
                    it
                )
            } ?: UiResult.Error()
        } catch (e: Throwable) {
            return UiResult.Error(
                IOException(
                    "Error logging in",
                    e
                )
            )
        }
    }
}

