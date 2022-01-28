package com.sayproj.memorynotes.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sayproj.memorynotes.R
import com.sayproj.memorynotes.databinding.FragmentListBinding
import com.sayproj.memorynotes.framework.ListViewModel
import com.sayproj.memorynotes.presentation.adapter.NoteListAdapter
import com.sayproj.memorynotes.viewBinding

class ListFragment : Fragment(R.layout.fragment_list), ListAction {
    private val noteListAdapter by lazy { NoteListAdapter(this) }
    private val binding: FragmentListBinding by viewBinding()
    private val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteListAdapter
        }
        binding.addNote.setOnClickListener { goToDetails() }
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun observeViewModel() = viewModel.notes.observe(viewLifecycleOwner) { notesList ->
        binding.loadingView.visibility = View.GONE
        binding.notesListView.visibility = View.VISIBLE
        noteListAdapter.submitList(notesList.sortedByDescending { it.updateTime })
    }

    private fun goToDetails(id: Long = 0L) {
        val action = ListFragmentDirections.actionGoToNote(id)
        findNavController().navigate(action)
    }

    override fun onClick(id: Long) {
        goToDetails(id)
    }
}
