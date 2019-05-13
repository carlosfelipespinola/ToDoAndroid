package com.example.letsdo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdo.data.ToDoTask
import com.example.letsdo.databinding.ToDoTaskItemBinding

interface ToDoTaskClickListener {
    fun onRootClick(toDoTask: ToDoTask)

    fun onCheckboxClick(toDoTask: ToDoTask, newCheckboxValue: Boolean)
}

class ToDoTaskAdapter(private val toDoTaskClickListener: ToDoTaskClickListener): RecyclerView.Adapter<ToDoTaskAdapter.ToDoTaskViewHolder>() {

    var itens: List<ToDoTask> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoTaskViewHolder {
        return ToDoTaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ToDoTaskViewHolder, position: Int) {
        holder.bind(itens[position], toDoTaskClickListener)
    }

    override fun getItemCount(): Int {
        return itens.size
    }

    class ToDoTaskViewHolder private constructor(private val binding: ToDoTaskItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(toDoTask: ToDoTask, toDoTaskClickListener: ToDoTaskClickListener){
            binding.todoTask = toDoTask
            binding.root.setOnClickListener { toDoTaskClickListener.onRootClick(toDoTask) }
            binding.taskStatus.setOnCheckedChangeListener { buttonView, isChecked ->
                toDoTaskClickListener.onCheckboxClick(toDoTask, isChecked)
            }
        }


        companion object {
            fun from(parent: ViewGroup): ToDoTaskViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ToDoTaskItemBinding.inflate(inflater, parent, false)
                return ToDoTaskViewHolder(binding)
            }
        }
    }

}

