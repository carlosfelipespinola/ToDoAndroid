package com.example.letsdo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.letsdo.data.ToDo
import com.example.letsdo.databinding.EditToDoFragmentBinding


class EditToDoFragment : Fragment() {

    companion object {
        fun newInstance() = EditToDoFragment()
    }

    private lateinit var viewModel: EditToDoViewModel
    private lateinit var binding: EditToDoFragmentBinding
    private var optionsMenu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_to_do_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditToDoViewModel::class.java)
        val args = EditToDoFragmentArgs.fromBundle(arguments!!)
        viewModel.setToDoUid(args.toDoUid)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeToDoUpdated()
        observeToDoUpdateError()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        optionsMenu = menu
        inflater.inflate(R.menu.create_to_do_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.done){
            onDoneButtonClicked()
            return true
        }
        return false
    }

    private fun onDoneButtonClicked(){
        if(toDoNameInputIsNotValid()){
            showInputNameError()
            return
        }
        setDoneButtonEnabled(false)
        viewModelUpdateToDo()
    }

    private fun toDoNameInputIsNotValid(): Boolean{
        return binding.inputName.text.isNullOrEmpty()
    }

    private fun showInputNameError(){
        binding.inputName.error = "Campo obrigatório"
    }

    private fun setDoneButtonEnabled(value: Boolean){
        val doneButtonMenuItem = optionsMenu?.findItem(R.id.done)
        doneButtonMenuItem?.isEnabled = value
    }

    private fun viewModelUpdateToDo(){
        viewModel.updateToDo(ToDo(binding.inputName.text.toString()))
    }

    private fun observeToDoUpdated(){
        setDoneButtonEnabled(true)
        viewModel.onToDoUpdated.observe(this, Observer { navigateToToDoFragment() })
    }

    private fun navigateToToDoFragment(){
        findNavController().popBackStack()
    }

    private fun observeToDoUpdateError(){
        viewModel.onToDoUpdateError.observe(this, Observer { showUpdateErrorMessage() })
    }

    private fun showUpdateErrorMessage(){
        Toast.makeText(context, "Update has failed, try again", Toast.LENGTH_LONG).show()
    }

}
