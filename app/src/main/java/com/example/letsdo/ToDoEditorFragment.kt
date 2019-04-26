package com.example.letsdo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf


class ToDoEditorFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoEditorFragment()
    }

    private lateinit var viewModel: ToDoEditorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.to_do_editor_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = ToDoEditorFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context, "todo list of uid: ${args.toDoUid}", Toast.LENGTH_LONG).show()
        viewModel = ViewModelProviders.of(this).get(ToDoEditorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
