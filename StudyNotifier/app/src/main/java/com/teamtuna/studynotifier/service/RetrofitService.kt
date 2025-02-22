package com.teamtuna.studynotifier.service

import com.teamtuna.studynotifier.ui.data.model.ApiResult
import com.teamtuna.studynotifier.ui.data.model.Member
import com.teamtuna.studynotifier.ui.data.model.Study
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    // 회사 proxy
    //const val END_POINT = "http://10.205.135.66:8080"

    // ngrok
    const val END_POINT = "http://b2c3eb6c.ngrok.io"

    private val retrofit =
            Retrofit.Builder()
                    .baseUrl(END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    private val service: StudyNotifierApi = retrofit.create(StudyNotifierApi::class.java)


    /**
     * Member
     */
    suspend fun login(email: String, pwd: String): ApiResult<Member> {
        return service.login(email, pwd)
    }

    suspend fun signUp(member: Member): ApiResult<Member> {
        return service.signUp(member)
    }


    /**
     * Push
     */
    suspend fun addPush(memberId: Long, msg: String) {
        service.addPush(memberId, msg)
    }


    /**
     * Study
     */
    suspend fun addStudy(study: Study): ApiResult<Study> {
        return service.addStudy(study)
    }
}