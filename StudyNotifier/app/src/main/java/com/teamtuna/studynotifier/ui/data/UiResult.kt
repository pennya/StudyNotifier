package com.teamtuna.studynotifier.ui.data

import com.teamtuna.studynotifier.ui.data.model.ApiErrorCode
import com.teamtuna.studynotifier.ui.data.model.ApiErrorType

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class UiResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : UiResult<T>()
    data class Error(
            val exception: Exception? = Exception("None Exception"),
            val apiErrorType : ApiErrorType = ApiErrorType.UNKNOWN,
            val apiErrorCode : ApiErrorCode = ApiErrorCode.UNKNOWN_SERVER_ERROR
    ) : UiResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
