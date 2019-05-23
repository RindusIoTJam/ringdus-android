package de.rindus.ringdus.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.rindus.ringdus.data.error.Failure

abstract class BaseViewModel : ViewModel() {
    private val failure: MutableLiveData<Failure> = MutableLiveData()

    fun observeFailure(): LiveData<Failure> = failure

    protected open fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    override fun onCleared() {
        super.onCleared()
        cancelRequest()
    }

    abstract fun cancelRequest()
}