package com.renatsayf.androidcheatsheet.ui.sections.webview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewViewModel : ViewModel() {

    sealed class State {
        data class PageStarted(val url: String): State()
        data class PageFinished(val url: String): State()
    }

    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun setState(state: State) {
        _state.value = state
    }
}