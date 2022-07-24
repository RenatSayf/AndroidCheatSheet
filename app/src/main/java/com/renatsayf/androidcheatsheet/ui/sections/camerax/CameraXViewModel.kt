package com.renatsayf.androidcheatsheet.ui.sections.camerax

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CameraXViewModel(private val app: Application) : AndroidViewModel(app) {

    sealed class State {
        object NotReady: State()
        object OnReady: State()
        object OnStartPhoto: State()
        object OnPhotoCompleted: State()
    }

    private var _state = MutableLiveData<State>(State.NotReady)
    val state: LiveData<State> = _state
    fun setState(state: State) {
        _state.value = state
    }

}