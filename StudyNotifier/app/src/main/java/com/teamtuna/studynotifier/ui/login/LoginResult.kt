package com.teamtuna.studynotifier.ui.login

import com.teamtuna.studynotifier.ui.data.model.Member

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val success: Member? = null,
        val error: Int? = null
)
