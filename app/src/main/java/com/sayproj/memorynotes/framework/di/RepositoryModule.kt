package com.sayproj.memorynotes.framework.di

import android.app.Application
import com.sayproj.core.repository.NoteRepository
import com.sayproj.memorynotes.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule() {
    @Provides
    fun provideRepository(app : Application) = NoteRepository(RoomNoteDataSource(app))
}
