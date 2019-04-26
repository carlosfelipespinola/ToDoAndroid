package com.example.letsdo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.letsdo.databinding.CreateToDoFragmentBinding


class CreateToDoFragment : Fragment() {

    companion object {
        fun newInstance() = CreateToDoFragment()
    }

    private lateinit var viewModel: CreateToDoViewModel
    private lateinit var binding: CreateToDoFragmentBinding
    private var optionsMenu: Menu? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_to_do_fragment, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateToDoViewModel::class.java)
        binding.lifecycleOwner = this
        observeOnToDoInsertSuccess()
        observeOnToDoInsertError()

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
        viewModelInsertNewToDo()
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

    private fun viewModelInsertNewToDo(){
        viewModel.newToDo.name = binding.inputName.text.toString()
        viewModel.insertToDo()
    }

    //warning: this method must be called only once
    private fun observeOnToDoInsertError(){
        viewModel.onToDoInsertError.observe(this, Observer {
            setDoneButtonEnabled(true)
            Toast.makeText(context, "Aconteceu um erro ao criar To-Do, tente novamente", Toast.LENGTH_LONG).show()
        })
    }

    //warning: this method must be called only once
    private fun observeOnToDoInsertSuccess(){
        viewModel.onToDoInserted.observe(this, Observer {
            navigateToToDoEditor(it)
        })
    }

    private fun navigateToToDoEditor(toDoUid: Long){
        val navController = findNavController()
        navController.navigate(CreateToDoFragmentDirections.actionCreateToDoFragmentToToDoEditorFragment(toDoUid))
    }

}
