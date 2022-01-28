package com.sayproj.core.usecase

import com.sayproj.core.data.Note
import com.sayproj.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}
