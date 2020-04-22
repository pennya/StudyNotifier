package com.teamtuna.StudyNotifierApiServer.service

import org.codehaus.jettison.json.JSONArray
import org.codehaus.jettison.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


@Service
class PushService {

    @Autowired
    lateinit var pushRepository: PushRepository

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Async
    fun sendMessage(entity: HttpEntity<String>): CompletableFuture<String>? {
        val interceptors = ArrayList<ClientHttpRequestInterceptor>()
        interceptors.add(HeaderRequestInterceptor("Authorization", "key=$FIREBASE_SERVER_KEY"))
        interceptors.add(HeaderRequestInterceptor("Content-Type", "application/json; UTF-8 "))
        restTemplate.interceptors = interceptors
        val firebaseResponse: String? = restTemplate.postForObject(FIREBASE_API_URL, entity, String::class.java)
        return CompletableFuture.completedFuture(firebaseResponse)
    }

    fun sendMessage(): String {
        // TODO  push table 에서 push 보낼 값 있는지 확인하고 유저 엔티티에서 토큰 값 구해서 보내기
        val deviceTokens = mutableListOf<String>()
        var name = ""

        // 가입된 회원의 모든 디바이스 리스트
        for (member in memberRepository.findAll()) {
            deviceTokens.add(member.deviceToken)
        }

        for (push in pushRepository.findAll()) {
            val member = memberRepository.findById(push.memberId)
            name = member.get().name

            val json = createPushJson(deviceTokens, "$name 님의 메시지입니다.", "내용입니다")
            val request = HttpEntity(json)

            // Firebase api call
            val pushNotification: CompletableFuture<String>? = sendMessage(request)
            CompletableFuture.allOf(pushNotification).join()

            try {
                val firebaseResponse = pushNotification?.get()
                println(ResponseEntity(firebaseResponse, HttpStatus.OK))
                // 메시지가 정상인지 실패인지 기록
                return "Success"

            } catch (e: Exception) {
                e.printStackTrace()
                return "Fail"
            }
        }

        return "Nothing"
    }

    fun sendMessageForTest(): ResponseEntity<String> {
        val deviceToken = "dBV592t7Fj0:APA91bEhqabCWVZrbtywOjxfJvy8HnvTK8bRK5yV2ipwZblv6okQDeDYt9TMNKwxQJNToZeyRd6iSxCaBmyvQA8fa8S_aj_A2vvU2OwNKdvdEK8Yj5WuVH_q8QLJHNhCaPeEgFkzARHi"
        val deviceTokens = mutableListOf<String>()
        val name = "홍길동"

        deviceTokens.add(deviceToken)
        // 가입된 회원의 모든 디바이스 리스트
        for (member in memberRepository.findAll()) {
            deviceTokens.add(member.deviceToken)
        }
        println(deviceTokens)

        val json = createPushJson(deviceTokens, "$name 님의 메시지입니다.", "내용입니다")
        val request = HttpEntity(json)

        // Firebase api call
        val pushNotification: CompletableFuture<String>? = sendMessage(request)
        CompletableFuture.allOf(pushNotification).join()

        try {
            val firebaseResponse = pushNotification!!.get()
            return ResponseEntity(firebaseResponse, HttpStatus.OK)
        } catch (e: InterruptedException) {
            throw InterruptedException()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        return ResponseEntity("Push Notification ERROR!", HttpStatus.BAD_REQUEST)
    }

    fun createPushJson(deviceTokens: List<String>, titleMsg: String, bodyMsg: String): String {
        val body = JSONObject()
        val tokenlist = mutableListOf<String>()
        for (i in deviceTokens.indices) {
            tokenlist.add(deviceTokens[i])
        }

        val array = JSONArray()
        for (i in tokenlist.indices) {
            array.put(tokenlist[i])
        }

        body.put("registration_ids", array)

        val notification = JSONObject().apply {
            put("title", titleMsg)
            put("body", bodyMsg)
        }

        body.put("notification", notification)

        println(body.toString())

        return body.toString()
    }

    companion object {
        private const val FIREBASE_SERVER_KEY = "AAAA6OGGnJ4:APA91bGmsYNB8DBE8wt945d0X09vt-0H4wx36q8p9o7Vq_987osUFM_6JAWfmZUNOz21jGL7ORyzYgjjsKJAm87wJaLm-Y-gfQtZqts_o1Rs1EuWO9dBMwCwZ7qSjBRj3U-8fq2atf5i"
        private const val FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send"
    }
}