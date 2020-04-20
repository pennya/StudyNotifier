package com.teamtuna.studynotifier.base

import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.teamtuna.studynotifier.R

open class BaseActivity: AppCompatActivity() {

    lateinit var mActivity: BaseActivity
    lateinit var mContext: Context
    private var destoried: Boolean = false
    private val mProgress by lazy { createProgress() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = this
        mContext = this
    }

    private fun createProgress(): AppCompatDialog {
        return with(AlertDialog.Builder(this)) {
            setCancelable(true)
            setView(ProgressBar(context, null, R.attr.progressBarStyle))
            create().apply {
                window?.setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.translucent))
                setCanceledOnTouchOutside(false)
            }
        }
    }

    fun showProgress() {
        if (lifecycle.currentState === Lifecycle.State.DESTROYED) return
        if (isFinishing) return
        if (destoried) return

        mProgress.takeUnless { mProgress.isShowing }?.show()
    }

    fun dismissProgress() {
        mProgress.takeIf { mProgress.isShowing }?.dismiss()
    }

}