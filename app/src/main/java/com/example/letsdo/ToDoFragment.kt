package com.example.letsdo

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.letsdo.databinding.ToDoFragmentBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdo.data.ToDoTask


class ToDoFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoFragment()
    }

    private lateinit var viewModel: ToDoViewModel
    private lateinit var binding: ToDoFragmentBinding
    private lateinit var adapter: ToDoTaskAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as Activity).application
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        setUpBinding(inflater, container)
        setUpBindingButtonsEvents()
        val args = ToDoFragmentArgs.fromBundle(arguments!!)
        setUpViewModel(requireActivity().application, args.toDoUid)
        binding.viewmodel = viewModel
        setUpRecyclerView()
        observeToDoTasksAndUpdateRecyclerView()
        observeToDoAndUpdateTitle()
        observeOnToDoDeletedAndCloseFragment()
        observeOnToDoDeleteErrorAndShowErrorMessage()
        return binding.root
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?){
        binding = DataBindingUtil.inflate(inflater, R.layout.to_do_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpBindingButtonsEvents(){
        binding.actionButton.setOnClickListener { navigateToDoTaskEditor(null) }
    }

    private fun navigateToDoTaskEditor(toDoTask: ToDoTask?){
        if(toDoTask == null){
            findNavController().navigate(
                ToDoFragmentDirections
                    .actionToDoEditorFragmentToToDoTaskEditorFragment2(viewModel.lodedToDoUid)
            )
        }else{
            findNavController().navigate(
                ToDoFragmentDirections
                    .actionToDoEditorFragmentToToDoTaskEditorFragment2(viewModel.lodedToDoUid, toDoTask.uid)
            )
        }
    }

    private fun setUpViewModel(application: Application, toDoUid: Long){
        val viewModelFactory = ToDoViewModelFactory(application, toDoUid)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToDoViewModel::class.java)
    }

    private fun setUpRecyclerView(){
        adapter = ToDoTaskAdapter(object : ToDoTaskClickListener {
            override fun onRootClick(toDoTask: ToDoTask) {
                navigateToDoTaskEditor(toDoTask)
            }

            override fun onCheckboxClick(toDoTask: ToDoTask, newCheckboxValue: Boolean) {
                toDoTask.isFinished = newCheckboxValue
                viewModelUpdateToDoTask(toDoTask)
            }
        })
        binding.toDoTaskRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.toDoTaskRecyclerView.layoutManager = layoutManager
        binding.toDoTaskRecyclerView.adapter = adapter
    }

    private fun viewModelUpdateToDoTask(toDoTask: ToDoTask){
        viewModel.updateToDoTask(toDoTask)
    }

    private fun observeToDoTasksAndUpdateRecyclerView(){
        viewModel.toDoTasks.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.itens = it }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate( R.menu.to_do_options_menu, menu)

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


    private fun observeOnToDoDeletedAndCloseFragment(){
        viewModel.onToDoDeleted.observe(this, Observer { navigateToToDosFragment() })
    }

    private fun navigateToToDosFragment(){
        val navController = findNavController()
        navController.navigate(ToDoFragmentDirections.actionToDoEditorFragmentToToDosFragment())
    }

    private fun observeOnToDoDeleteErrorAndShowErrorMessage(){
        viewModel.onToDoDeleteError.observe(this, Observer { showToDoDeleteErrorMessage()  })
    }

    private fun showToDoDeleteErrorMessage(){
        viewModel.onToDoDeleteError.observe(this, Observer {
            Toast.makeText(context, "Error while deleting To-do list", Toast.LENGTH_LONG).show()
        })
    }

    private fun observeToDoAndUpdateTitle(){
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
