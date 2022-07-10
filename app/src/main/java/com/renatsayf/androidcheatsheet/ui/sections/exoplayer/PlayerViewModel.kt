package com.renatsayf.androidcheatsheet.ui.sections.exoplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    sealed class State {
        object Loading: State()
        data class Current(val playerState: PlayerState): State()
    }

    data class PlayerState(
        val playWhenReady: Boolean = true,
        val currentItem: Int = 0,
        val playbackPosition: Long = 0L
    )

    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    fun setState(state: State) {
        _state.value = state
    }
}