package com.teamtuna.studynotifier.viewmodel

import com.teamtuna.studynotifier.base.BaseViewModel
import com.teamtuna.studynotifier.service.RetrofitService
import com.teamtuna.studynotifier.util.getLoggedInMemberId
import com.teamtuna.studynotifier.util.isLoggedIn
import kotlinx.coroutines.launch

class PushViewModel: BaseViewModel() {

    fun addPushMessage(msg: String) {
        uiScope.launch(ioDispatchers) {
            if (isLoggedIn()) {
                getLoggedInMemberId()?.let { loggedInMemberId ->
                    RetrofitService.addPush(loggedInMemberId, msg)
                }
            }
        }
    }
}