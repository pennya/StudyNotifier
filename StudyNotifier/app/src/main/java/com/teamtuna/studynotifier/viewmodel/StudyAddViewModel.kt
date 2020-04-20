package com.teamtuna.studynotifier.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamtuna.studynotifier.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class StudyAddViewModel: BaseViewModel() {

    val _study = MutableLiveData<String>()
    val study: LiveData<String> get() = _study

    fun addStudy() {
        uiScope.launch(uiDispatchers) {
            // progress visible
            _dataLoading.value = true

            // network call
            uiScope.launch(ioDispatchers) {
                delay(3000)
                _study.postValue("study add success!")

                // progress gone
                _dataLoading.postValue(false)
            }
        }
    }
}