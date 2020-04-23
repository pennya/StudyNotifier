package com.teamtuna.studynotifier.ui.data.login

import com.teamtuna.studynotifier.ui.data.UiResult
import com.teamtuna.studynotifier.ui.data.model.LoggedInMember
import com.teamtuna.studynotifier.ui.data.model.Member

class LoginRepository(val dataSource: LoginDataSource) {

    var member: Member? = null
        private set

    suspend fun login(username: String, password: String): UiResult<Member> {
        val result = dataSource.login(username, password)

        if (result is UiResult.Success) {
            member = result.data
            LoggedInMember.member = member
        }

        return result
    }
}
