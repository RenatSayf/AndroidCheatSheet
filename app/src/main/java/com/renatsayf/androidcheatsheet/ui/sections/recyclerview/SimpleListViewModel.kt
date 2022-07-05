package com.renatsayf.androidcheatsheet.ui.sections.recyclerview

import androidx.lifecycle.*
import com.renatsayf.androidcheatsheet.data.db.room.java.ArticleEntity
import com.renatsayf.androidcheatsheet.di.manually.DbModuleJava
import com.renatsayf.androidcheatsheet.domain.db.java.DbRepository
import kotlinx.coroutines.launch

class SimpleListViewModel(private val dbRepository: DbRepository) : ViewModel() {

    class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SimpleListViewModel::class.java)
            return SimpleListViewModel(dbRepository = DbModuleJava.getDbRepository()) as T
        }
    }

    fun writeIntoDb(articles: List<ArticleEntity>) {
        //region Hint Room_Writing_into_database
        dbRepository.insertAll(articles)
        //endregion Room_Writing_into_database
    }

    fun getAllArticles(): LiveData<List<ArticleEntity>> {
        val articles = MutableLiveData<List<ArticleEntity>>()
        viewModelScope.launch {
            //region Hint Room_Getting_all_from_database
            articles.value = dbRepository.all
            //endregion Room_Getting_all_from_database
        }
        return articles
    }
}