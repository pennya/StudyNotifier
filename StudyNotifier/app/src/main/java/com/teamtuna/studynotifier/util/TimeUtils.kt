package com.teamtuna.studynotifier.util

fun milliSecondsToString(milliSeconds: Long): String {
    val second: Long = milliSeconds / 1000 % 60
    val minute: Long = milliSeconds / (1000 * 60) % 60
    val hour: Long = milliSeconds / (1000 * 60 * 60) % 24

    return String.format("%02d : %02d : %02d", hour, minute, second)
}


fun getStartTime() = System.currentTimeMillis()

fun getLastTime(runningTime: Long) = getStartTime() + runningTime