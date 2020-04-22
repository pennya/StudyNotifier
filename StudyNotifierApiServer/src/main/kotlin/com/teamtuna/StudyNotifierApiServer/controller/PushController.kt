package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.service.PushService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PushController {

    @Autowired
    lateinit var pushService: PushService

    @RequestMapping(value = ["/rest/v1/push"], method = [RequestMethod.POST])
    fun addPushMessage(memberId: Long, msg: String) {
        pushService.addPushMessage(memberId, msg)
    }

    //@Scheduled(fixedDelay = 60_000 * 5)
    @Scheduled(fixedDelay = 10_000)
    @RequestMapping(value = ["/rest/v1/push"], method = [RequestMethod.GET])
    @ResponseBody fun send(): String {
        return pushService.sendMessage()
    }

    //@Scheduled(fixedDelay = 10_000)
    @RequestMapping(value = ["/rest/v1/push_test"], method = [RequestMethod.GET])
    @ResponseBody fun sendForTest(): ResponseEntity<String> {
        return pushService.sendMessageForTest()
    }
}