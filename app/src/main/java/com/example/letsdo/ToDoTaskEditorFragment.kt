package com.example.letsdo

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.letsdo.databinding.ToDoTaskEditorFragmentBinding


class ToDoTaskEditorFragment : Fragment() {

    companion object {
        fun newInstance() = ToDoTaskEditorFragment()
    }

    private lateinit var viewModel: ToDoTaskEditorViewModel
    private lateinit var binding: ToDoTaskEditorFragmentBinding
    private lateinit var args: ToDoTaskEditorFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        setUpBinding(inflater, container)
        setUpArgs()
        setUpViewModel(requireActivity().application, args.ToDoUid)
        binding.viewModel = viewModel
        loadToDoTaskToEditIfReceivedToDoTaskUidInArguments()
        return binding.root
    }

    private fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?){
        binding = DataBindingUtil.inflate(inflater, R.layout.to_do_task_editor_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun setUpArgs(){
        args = ToDoTaskEditorFragmentArgs.fromBundle(arguments!!)
    }

    private fun setUpViewModel(application: Application, todoUid: Long){
        val viewModelFactory = ToDoTaskEditorViewModelFactory(application, todoUid)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToDoTaskEditorViewModel::class.java)
    }

    private fun loadToDoTaskToEditIfReceivedToDoTaskUidInArguments(){
        if(wasToDoTaskEditorOpenForEdit(args.ToDoTaskUid)){
            loadToDoTaskForEdit(args.ToDoTaskUid)
        }
    }

    private fun wasToDoTaskEditorOpenForEdit(toDoTaskUidReceived: Long): Boolean {
        return toDoTaskUidReceived != -1L
    }

    private fun loadToDoTaskForEdit(toDoTaskUidReceived: Long){
        viewModel.loadExistingToDoTask(toDoTaskUidReceived)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.to_do_task_editor, menu)
        hideDeleteOptionIfEditorWasNotOpenedForEdit(menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun hideDeleteOptionIfEditorWasNotOpenedForEdit(menu: Menu){
        if(wasToDoTaskEditorOpenForEdit(args.ToDoTaskUid).not()){
            menu.findItem(R.id.delete).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.done){
            save()
            return true
        }

        if(item.itemId == R.id.delete){
            delete()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save(){
        viewModel.insertOrUpdateToDoTask()
        findNavController().popBackStack()
    }

    private fun delete(){
        viewModel.deleteToDoTask()
        findNavController().popBackStack()
    }
}
