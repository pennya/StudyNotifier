package com.teamtuna.studynotifier.ui.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teamtuna.studynotifier.base.BaseViewModel
import com.teamtuna.studynotifier.service.RetrofitService
import com.teamtuna.studynotifier.ui.data.UiResult
import com.teamtuna.studynotifier.ui.data.model.LoggedInMember
import com.teamtuna.studynotifier.ui.data.model.Study
import com.teamtuna.studynotifier.util.getLastTime
import com.teamtuna.studynotifier.util.getStartTime
import kotlinx.coroutines.launch

class StudyViewModel: BaseViewModel() {

    private val _studyForm = MutableLiveData<StudyFormState>()
    val studyFormState: LiveData<StudyFormState> = _studyForm

    val _study = MutableLiveData<UiResult<Study>>()
    val study: LiveData<UiResult<Study>> get() = _study

    fun studyDataChanged(title: String) {
        if (isTitleValid(title)) {
            _studyForm.value = StudyFormState(isDataValid = true)
        } else {
            _studyForm.value = StudyFormState(titleError = "필수값입니다.")
        }
    }

    fun addStudy(title: String, description: String, runningTime: Long) {
        if (!isTitleValid(title)) {
            _studyForm.value = StudyFormState(titleError = "필수값입니다.")
            return
        }

        uiScope.launch(uiDispatchers) {
            // progress visible
            _dataLoading.value = true

            // network call
            uiScope.launch(ioDispatchers) {
                val study = Study(
                    startTime = getStartTime(),
                    endTime = getLastTime(runningTime),
                    date = getStartTime(),
                    title = title,
                    description = description,
                    member = LoggedInMember.member
                )

                val studyResult = RetrofitService.addStudy(study)
                studyResult.data?.let {
                    _study.postValue(UiResult.Success(it))
                } ?: _study.postValue(UiResult.Error(Exception("fail to get study data")))

                // progress gone
                _dataLoading.postValue(false)
            }
        }
    }

    private fun isTitleValid(title: String): Boolean {
        return title.isNotEmpty()
    }
}