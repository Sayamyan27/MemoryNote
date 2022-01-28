package com.sayproj.memorynotes.framework

import com.sayproj.core.usecase.*

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val wordCount: GetWordCount
)
