package com.sayproj.memorynotes.framework.di

import com.sayproj.memorynotes.framework.ListViewModel
import com.sayproj.memorynotes.framework.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class,RepositoryModule::class,UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}
