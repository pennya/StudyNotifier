package com.teamtuna.studynotifier.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel: ViewModel() {

    /**
     * CoroutineScope 내부 Exception 처리 Handler
     */
    private val coroutineExceptionHanlder = CoroutineExceptionHandler { coroutineContext, throwable ->
        throwable.printStackTrace()
    }

    /**
     * Dispatchers 선언 (Normal Dispatchers + CoroutineExceptionHandler)
     */
    protected val ioDispatchers = Dispatchers.IO + coroutineExceptionHanlder
    protected val uiDispatchers = Dispatchers.Main + coroutineExceptionHanlder

    /*
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /*
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    protected val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // true : VISIBLE, false : GONE
    protected val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> get() = _dataLoading

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}