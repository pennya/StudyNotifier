package com.teamtuna.studynotifier.ui

import android.os.Bundle
import com.teamtuna.studynotifier.R
import com.teamtuna.studynotifier.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }
}
