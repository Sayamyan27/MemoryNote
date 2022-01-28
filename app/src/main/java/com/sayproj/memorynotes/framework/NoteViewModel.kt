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

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val saved = MutableLiveData<Boolean>()
    val currentNote = MutableLiveData<Note?>()
    @Inject
    lateinit var useCases : UseCases

    init {
        DaggerViewModelComponent.builder()
            .applicationModule(ApplicationModule(getApplication()))
            .build()
            .inject(this)
    }
    fun saveNote(note: Note) = coroutineScope.launch {
        useCases.addNote(note)
        saved.postValue(true)
    }

    fun getNote(id: Long) = coroutineScope.launch {
        val note = useCases.getNote(id)
        currentNote.postValue(note)
    }

    fun deleteNote(note: Note){
        coroutineScope.launch {
            useCases.removeNote(note)
            saved.postValue(true)
        }
    }
}
