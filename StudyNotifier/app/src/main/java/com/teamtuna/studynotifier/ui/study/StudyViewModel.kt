package com.teamtuna.studynotifier.ui.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamtuna.studynotifier.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StudyViewModel: BaseViewModel() {

    private val _studyForm = MutableLiveData<StudyFormState>()
    val studyFormState: LiveData<StudyFormState> = _studyForm

    val _study = MutableLiveData<String>()
    val study: LiveData<String> get() = _study

    fun studyDataChanged(title: String) {
        if (isTitleValid(title)) {
            _studyForm.value = StudyFormState(isDataValid = true)
        } else {
            _studyForm.value = StudyFormState(titleError = "필수값입니다.")
        }
    }

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

    private fun isTitleValid(title: String): Boolean {
        return title.isNotEmpty()
    }
}