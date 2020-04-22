package com.teamtuna.studynotifier.service

import com.teamtuna.studynotifier.ui.data.model.ApiResult
import com.teamtuna.studynotifier.ui.data.model.Member
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface StudyNotifierApi {

    @POST("/rest/v1/member")
    suspend fun signUp(
            @Body member: Member
    ): ApiResult<Member>

    @FormUrlEncoded
    @POST("/rest/v1/member_login")
    suspend fun login(
            @Field("email") email: String,
            @Field("pwd") pwd: String
    ): ApiResult<Member>
}