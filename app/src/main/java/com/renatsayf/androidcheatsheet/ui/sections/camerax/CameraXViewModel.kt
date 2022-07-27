package com.renatsayf.androidcheatsheet.ui.sections.camerax

import android.app.Application
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.renatsayf.androidcheatsheet.ui.sections.extentions.appPicturesDirName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class CameraXViewModel(private val app: Application) : AndroidViewModel(app) {

    sealed class State {
        object NotReady: State()
        object OnReady: State()
        object OnStartPhoto: State()
        data class OnPhotoCompleted(val uri: Uri?): State()
    }

    private var _state = MutableLiveData<State>(State.NotReady)
    val state: LiveData<State> = _state
    fun setState(state: State) {
        _state.value = state
    }

    private var _photoUriList = MutableLiveData<MutableList<Uri>>(mutableListOf())
    val photoUriList: LiveData<MutableList<Uri>> = _photoUriList
    fun addUriToList(uri: Uri) {
        _photoUriList.value?.add(uri)
    }

    fun getAppPictures(): MutableLiveData<List<File>> {
        val files = MutableLiveData<List<File>>(listOf())
        viewModelScope.launch(Dispatchers.IO) {
            val file = Environment.getExternalStoragePublicDirectory(app.appPicturesDirName)
            val list = file.listFiles()?.toList()
            files.postValue(list)
        }
        return files
    }

}