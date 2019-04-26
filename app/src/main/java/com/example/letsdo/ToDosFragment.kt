package com.example.letsdo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.letsdo.databinding.ToDosFragmentBinding


class ToDosFragment : Fragment() {

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
        //binding.actionButtonClick = View.OnClickListener { navigateToCreateToDoFragment() }
        // TODO: Use the ViewModel
    }

     private fun navigateToCreateToDoFragment(){
        val navController = findNavController()
        navController.navigate(ToDosFragmentDirections.actionToDosFragmentToCreateToDoFragment())
    }

}
