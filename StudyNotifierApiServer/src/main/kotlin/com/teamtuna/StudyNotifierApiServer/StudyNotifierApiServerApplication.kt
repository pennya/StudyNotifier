package com.teamtuna.StudyNotifierApiServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class StudyNotifierApiServerApplication

fun main(args: Array<String>) {
	runApplication<StudyNotifierApiServerApplication>(*args)
}
