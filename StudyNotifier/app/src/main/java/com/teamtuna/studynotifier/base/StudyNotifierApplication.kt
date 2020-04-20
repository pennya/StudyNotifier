package com.teamtuna.studynotifier.base

import android.app.Application
import com.teamtuna.studynotifier.util.PP

class StudyNotifierApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        PP.CREATE(applicationContext)
    }
}