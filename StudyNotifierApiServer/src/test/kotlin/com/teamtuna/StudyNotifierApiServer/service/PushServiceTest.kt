package com.teamtuna.StudyNotifierApiServer.service

import com.teamtuna.StudyNotifierApiServer.domain.Push
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
internal class PushServiceTest {

    @Autowired
    lateinit var pushService: PushService

    @Autowired
    lateinit var pushRepository: PushRepository

    @Test
    fun `푸시 테이블 조회 및 비우기`() {

        val push = Push(memberId = 1, message = "push msg1")
        pushRepository.save(push)

        val push2 = Push(memberId = 1, message = "push msg2")
        pushRepository.save(push2)

        val pushList = pushRepository.findAll()
        for (_push in pushList) {
            println("#1 $_push")
            Assertions.assertThat(push.memberId).isEqualTo(_push.memberId)
            Assertions.assertThat(push.isSend).isEqualTo(_push.isSend)
        }

        val notSendPushList = pushRepository.findByIsSendFalse()
        for (_push in notSendPushList) {
            println("#2 $_push")
            Assertions.assertThat(push.isSend).isEqualTo(_push.isSend)
            _push.id?.let { pushService.updatePushStatus(it) }
        }

        val modifiedPushList = pushRepository.findAll()
        for (_push in modifiedPushList) {
            println("#3 $_push")
            Assertions.assertThat(push.isSend).isEqualTo(!_push.isSend)
        }
    }
}