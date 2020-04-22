package com.teamtuna.studynotifier.ui.data.model

data class ApiResult<out T : Any>(
        val data: T? = null,
        val error: ApiError? = null
)

data class ApiError(
        val exception: Exception? = Exception("None Exception"),
        val apiErrorType : ApiErrorType = ApiErrorType.UNKNOWN,
        val apiErrorCode : ApiErrorCode = ApiErrorCode.UNKNOWN_SERVER_ERROR
)

enum class ApiErrorCode {
    UNKNOWN_SERVER_ERROR,
    DUPLICATE_LOGIN_ID,
    INCORRECT_LOGIN_ID_AND_PASSWORD,
    INVALIDATE_ACCESS_TOKEN,
    EXPIRED_ACCESS_TOKEN,
    DUPLICATE_CODE,
    NO_PERMISSION,
    NOT_FOUND,
    DUPLICATE_DEVICE_ID,
    INCORRECT_LOGIN_ID,
    DUPLICATE_ID
}

enum class ApiErrorType {
    INVALIDATE_INPUT,
    MESSAGE,
    UNKNOWN,
    AUTHENTICATION
}