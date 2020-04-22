package com.teamtuna.studynotifier.ui.data

import com.teamtuna.studynotifier.service.RetrofitService
import com.teamtuna.studynotifier.ui.data.model.ApiErrorCode.INCORRECT_LOGIN_ID_AND_PASSWORD
import com.teamtuna.studynotifier.ui.data.model.Member
import com.teamtuna.studynotifier.util.PP
import java.io.IOException

class LoginDataSource {

    suspend fun login(email: String, pwd: String): Result<Member> {
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

            return member.data?.let { Result.Success(it) } ?: Result.Error()
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }
}

