package com.example.letsdo

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        val args = ToDoEditorFragmentArgs.fromBundle(arguments!!)
        viewModel = ViewModelProviders.of(this).get(ToDoEditorViewModel::class.java)
        viewModel.setToDoUid(toDoUid = args.toDoUid)
        binding.viewmodel = viewModel
        observeToDo()
        observeOnToDoDeleteError()
        observeOnToDoDeleted()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.to_do_editor_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delete_list){
            viewModelDeleteToDo()
            return true
        }
        return false
    }

    private fun viewModelDeleteToDo(){
        viewModel.deleteToDo()
    }

    private fun observeOnToDoDeleted(){
        viewModel.onToDoDeleted.observe(this, Observer {
            val navController = findNavController()
            navController.navigate(ToDoEditorFragmentDirections.actionToDoEditorFragmentToToDosFragment())
        })
    }

    private fun observeOnToDoDeleteError(){
        viewModel.onToDoDeleteError.observe(this, Observer {
            Toast.makeText(context, "Error while deleting To-do list", Toast.LENGTH_LONG).show()
        })
    }

    private fun observeToDo(){
        viewModel.toDo.observe(this, Observer {
            (activity as AppCompatActivity).supportActionBar?.title = it?.name
        })
    }







}
