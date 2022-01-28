package com.sayproj.memorynotes.framework.di

import com.sayproj.core.repository.NoteRepository
import com.sayproj.core.usecase.*
import com.sayproj.memorynotes.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}
