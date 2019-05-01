package com.example.letsdo

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.view.MenuItem.OnActionExpandListener
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.letsdo.data.ToDo
import com.example.letsdo.databinding.ToDoFragmentBinding


class ToDoFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoFragment()
    }

    private lateinit var viewModel: ToDoViewModel
    private lateinit var binding: ToDoFragmentBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as Activity).application
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.to_do_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = ToDoFragmentArgs.fromBundle(arguments!!)
        viewModel = ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        viewModel.setToDoUid(toDoUid = args.toDoUid)




        observeToDo()
        observeOnToDoDeleted()
        observeOnToDoDeleteError()

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.to_do_options_menu, menu)

        val searchView = menu.findItem(R.id.to_do_task_search).actionView as SearchView

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.edit){
            navigateToToDoEditor()
        }

        if(item.itemId == R.id.delete_list){
            viewModelDeleteToDo()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun navigateToToDoEditor(){
        val navController = findNavController()
        navController.navigate(
            ToDoFragmentDirections
                .actionToDoEditorFragmentToEditToDoFragment(viewModel.lodedToDoUid)
        )
    }

    private fun viewModelDeleteToDo(){
        viewModel.deleteToDo()
    }


    private fun observeOnToDoDeleted(){
        viewModel.onToDoDeleted.observe(this, Observer { navigateToToDosFragment() })
    }

    private fun navigateToToDosFragment(){
        val navController = findNavController()
        navController.navigate(ToDoFragmentDirections.actionToDoEditorFragmentToToDosFragment())
    }

    private fun observeOnToDoDeleteError(){
        viewModel.onToDoDeleteError.observe(this, Observer { showToDoDeleteErrorMessage()  })
    }

    private fun showToDoDeleteErrorMessage(){
        viewModel.onToDoDeleteError.observe(this, Observer {
            Toast.makeText(context, "Error while deleting To-do list", Toast.LENGTH_LONG).show()
        })
    }

    private fun observeToDo(){
        viewModel.toDo.observe(this, Observer {
            if(it != null){
                updateActionBarTitle(it.name)
            }
        })
    }

    private fun updateActionBarTitle(newTitle: String){
        (activity as AppCompatActivity).supportActionBar?.title = newTitle
    }





}
