package com.teamtuna.studynotifier.ui.data

import com.teamtuna.studynotifier.ui.data.model.Member

class LoginRepository(val dataSource: LoginDataSource) {

    var member: Member? = null
        private set

    suspend fun login(username: String, password: String): Result<Member> {
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            member = result.data
        }

        return result
    }
}
