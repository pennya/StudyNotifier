package com.teamtuna.StudyNotifierApiServer.controller

import com.teamtuna.StudyNotifierApiServer.domain.Study
import com.teamtuna.StudyNotifierApiServer.service.StudyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class StudyController {

    @Autowired
    private lateinit var studyService: StudyService

    @RequestMapping(value = ["/rest/v1/study"], method = [RequestMethod.POST])
    fun addStudy(@RequestBody study: Study): Study {
        return studyService.addStudy(study)
    }

    @RequestMapping(value = ["/rest/v1/study/{id}"], method = [RequestMethod.DELETE])
    fun deleteStudy(@PathVariable("id") id: Long) {
        return studyService.deleteStudy(id)
    }
}