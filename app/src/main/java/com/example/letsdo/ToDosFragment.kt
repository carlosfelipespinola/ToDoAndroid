package com.example.letsdo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letsdo.data.ToDo
import com.example.letsdo.databinding.ToDosFragmentBinding
import com.example.letsdo.interfaces.OnToDoItemClickListener


class ToDosFragment : Fragment(), OnToDoItemClickListener {

    companion object {
        fun newInstance() = ToDosFragment()
    }

    private lateinit var viewModel: ToDosViewModel
    private lateinit var binding: ToDosFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.to_dos_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ToDosViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.actionButton.setOnClickListener { navigateToCreateToDoFragment() }
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.adapter = ToDosRecyclerViewAdapter(toDos =  arrayListOf(), onToDoClickListener = this)
        observeToDos()
    }

    private fun navigateToCreateToDoFragment(){
        val navController = findNavController()
        navController.navigate(ToDosFragmentDirections.actionToDosFragmentToCreateToDoFragment())
    }

    private fun navigateToToDoEditorFragment(toDoUid: Long){
        val navController = findNavController()
        navController.navigate(ToDosFragmentDirections.actionToDosFragmentToToDoEditorFragment(toDoUid))
    }

    override fun onToDoItemClick(v: View?, toDo: ToDo) {
        navigateToToDoEditorFragment(toDo.uid)
    }

    private fun observeToDos(){
        viewModel.toDos.observe(this, Observer {
            val adapter = binding.mainRecyclerView.adapter as ToDosRecyclerViewAdapter
            adapter.toDos = it
        })
    }



}
