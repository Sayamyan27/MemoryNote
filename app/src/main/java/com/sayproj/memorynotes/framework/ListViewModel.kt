package com.sayproj.memorynotes.framework

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sayproj.core.data.Note
import com.sayproj.memorynotes.framework.di.ApplicationModule
import com.sayproj.memorynotes.framework.di.DaggerViewModelComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel(application : Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope((Dispatchers.IO))
    val notes = MutableLiveData<List<Note>>()
    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
    fun getNotes(){
        coroutineScope.launch {
            val noteList = useCases.getAllNotes()
            noteList.forEach { it.wordCount = useCases.wordCount.invoke(it) }
            notes.postValue(noteList)
        }
    }
}
