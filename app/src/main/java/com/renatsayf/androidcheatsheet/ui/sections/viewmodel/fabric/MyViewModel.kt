package com.renatsayf.androidcheatsheet.ui.sections.viewmodel.fabric

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renatsayf.androidcheatsheet.domain.NetRepository

@Suppress("UNCHECKED_CAST")
class MyViewModel(private val repository: NetRepository) : ViewModel() {

    //region Hint ViewModel.Factory step1
    class Factory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MyViewModel::class.java)
            return MyViewModel(repository = NetRepository()) as T
        }
    }
    //endregion

    sealed class State {
        object Loading: State()
    }

    private var _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    fun setState(state: State) {
        repository
        _state.value = state
    }


}

