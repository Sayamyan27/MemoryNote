package com.sayproj.memorynotes.presentation.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.sayproj.core.data.Note

class NotesDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Note, newItem: Note): Any? {
        val bundle = Bundle()

        if (newItem.title != oldItem.title) {
            bundle.putString(TITLE_DIFF, newItem.title)
        }
        if (newItem.content != oldItem.content) {
            bundle.putString(CONTENT_DIFF, newItem.content)
        }
        if (newItem.updateTime != oldItem.updateTime) {
            bundle.putString(UPDATE_DATE_DIFF, newItem.updateTime.toString())
        }
        if (newItem.wordCount != oldItem.wordCount) {
            bundle.putString(WORDS_COUNT_DIFF, newItem.wordCount.toString())
        }

        return bundle
    }

    companion object {
        const val TITLE_DIFF = "title_diff"
        const val CONTENT_DIFF = "content_diff"
        const val UPDATE_DATE_DIFF = "update_time_diff"
        const val WORDS_COUNT_DIFF = "words_count_diff"

    }
}