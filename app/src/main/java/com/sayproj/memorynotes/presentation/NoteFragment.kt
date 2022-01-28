package com.sayproj.memorynotes.presentation

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sayproj.core.data.Note
import com.sayproj.memorynotes.R
import com.sayproj.memorynotes.databinding.FragmentNoteBinding
import com.sayproj.memorynotes.framework.NoteViewModel
import com.sayproj.memorynotes.viewBinding

class NoteFragment : Fragment(R.layout.fragment_note) {
    private var noteId = 0L
    private lateinit var viewModel: NoteViewModel
    private val binding by viewBinding<FragmentNoteBinding>()
    private var currentNote = Note("", "", 0L, 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        binding.checkButton.setOnClickListener {
            if (!binding.titleView.text.isNullOrEmpty() || !binding.contentView.text.isNullOrEmpty()) {
                val time = System.currentTimeMillis()
                currentNote.apply {
                    title = binding.titleView.text.toString()
                    content = binding.contentView.text.toString()
                    updateTime = time
                }
                if (currentNote.creationTime == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                findNavController().popBackStack()
            }
        }
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteNote -> {
                if (context != null && noteId != 0L) {
                    showDeleteDialog()
                }
            }
        }
        return true
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.deleteNote))
            .setMessage(getString(R.string.areYouSure))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteNote(currentNote)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .create()
            .show()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, getString(R.string.done), Toast.LENGTH_SHORT).show()
                hideKeyBoard()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, getString(R.string.wentWrong), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.currentNote.observe(viewLifecycleOwner) { note ->
            note?.let {
                currentNote = it
                binding.titleView.setText(it.title, TextView.BufferType.EDITABLE)
                binding.contentView.setText(it.content, TextView.BufferType.EDITABLE)
            }
        }
    }

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.titleView.windowToken, 0)
    }
}
