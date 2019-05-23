package de.rindus.ringdus.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.None
import de.rindus.ringdus.domain.OpenDoor

class OpenDoorViewModel(private val openDoor: OpenDoor) : BaseViewModel() {

    private val success = MutableLiveData<None>()

    fun observeSuccess(): LiveData<None> = success

    fun doOpenDoor() {
        openDoor(None) { it.fold(::handleFailure, ::handleSuccess) }
    }

    private fun handleSuccess(data: None) {
        success.value = data
    }

    override fun cancelRequest() {
        openDoor.cancel()
    }
}