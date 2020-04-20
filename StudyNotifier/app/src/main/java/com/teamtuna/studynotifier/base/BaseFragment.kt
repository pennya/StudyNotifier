package com.teamtuna.studynotifier.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    lateinit var mActivity: BaseActivity
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = requireActivity() as BaseActivity
        mContext = requireContext()
    }

    fun showProgress() = mActivity.showProgress()
    fun dismissProgress() = mActivity.dismissProgress()
}