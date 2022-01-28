package com.sayproj.memorynotes.framework

import android.content.Context
import com.sayproj.core.data.Note
import com.sayproj.core.repository.NoteDataSource
import com.sayproj.memorynotes.framework.db.DatabaseService
import com.sayproj.memorynotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note = noteDao.getNoteEntity(id).toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}
