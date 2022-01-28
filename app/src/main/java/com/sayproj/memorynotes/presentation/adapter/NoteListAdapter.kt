package com.sayproj.memorynotes.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sayproj.core.data.Note
import com.sayproj.memorynotes.databinding.ItemNoteBinding
import com.sayproj.memorynotes.framework.getOrNull
import com.sayproj.memorynotes.presentation.ListAction
import java.text.SimpleDateFormat
import java.util.*

class NoteListAdapter(val actions: ListAction) :
    ListAdapter<Note, NoteListAdapter.NoteViewHolder>(NotesDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        val bundle = payloads[0] as? Bundle ?: return
        bundle.apply {
            getOrNull<String>(NotesDiffCallback.TITLE_DIFF)?.let {
                holder.updateTitle(it)
            }
            getOrNull<String>(NotesDiffCallback.CONTENT_DIFF)?.let {
                holder.updateContent(it)
            }
            getOrNull<String>(NotesDiffCallback.UPDATE_DATE_DIFF)?.let {
                holder.updateDate(it)
            }
            getOrNull<String>(NotesDiffCallback.WORDS_COUNT_DIFF)?.let {
                holder.updateWordCount(it)
            }
        }
    }

    inner class NoteViewHolder(
        private var binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            updateTitle(note.title)
            updateContent(note.content)
            updateDate(note.updateTime.toString())
            updateWordCount(note.wordCount.toString())

            binding.noteLayout.setOnClickListener {
                actions.onClick(note.id)
            }
        }

        fun updateTitle(title: String) {
            binding.title.text = title
        }

        fun updateContent(content: String) {
            binding.content.text = content
        }

        fun updateDate(date: String) {
            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault())
            val resultDate = Date(date.toLong())
            binding.date.text = "Last updated: ${sdf.format(resultDate)}"
        }

        fun updateWordCount(worCount: String) {
            binding.wordCount.text = "Words: $worCount"
        }
    }
}
