package com.example.letsdo

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.letsdo.databinding.ToDoEditorFragmentBinding


class ToDoEditorFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoEditorFragment()
    }

    private lateinit var viewModel: ToDoEditorViewModel
    private lateinit var binding: ToDoEditorFragmentBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as Activity).application
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.to_do_editor_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this

        val args = ToDoEditorFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(context, "todo list of uid: ${args.toDoUid}", Toast.LENGTH_LONG).show()
        viewModel = ViewModelProviders.of(this).get(ToDoEditorViewModel::class.java)
        viewModel.setToDoUid(toDoUid = args.toDoUid)
        binding.viewmodel = viewModel
        observeToDo()
    }

    private fun observeToDo(){
        viewModel.toDo.observe(this, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = it.name
        })
    }

}
