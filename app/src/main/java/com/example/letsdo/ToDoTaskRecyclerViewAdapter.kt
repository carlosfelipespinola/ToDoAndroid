package com.example.letsdo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdo.data.ToDoTask
import com.example.letsdo.databinding.ToDoTaskItemBinding

interface ToDoTaskClickListener {
    fun onRootClick(toDoTask: ToDoTask)

    fun onCheckboxChangeToDoTaskStatus(changedToDoTask: ToDoTask)
}

class ToDoTaskAdapter(private val toDoTaskClickListener: ToDoTaskClickListener): ListAdapter<ToDoTask,ToDoTaskAdapter.ToDoTaskViewHolder>(object: DiffUtil.ItemCallback<ToDoTask>(){
    override fun areItemsTheSame(oldItem: ToDoTask, newItem: ToDoTask): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: ToDoTask, newItem: ToDoTask): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoTaskViewHolder {
        return ToDoTaskViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ToDoTaskViewHolder, position: Int) {
        holder.bind(getItem(position), toDoTaskClickListener)
    }

    class ToDoTaskViewHolder private constructor(private val binding: ToDoTaskItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(toDoTask: ToDoTask, toDoTaskClickListener: ToDoTaskClickListener){
            binding.todoTask = toDoTask
            binding.root.setOnClickListener { toDoTaskClickListener.onRootClick(toDoTask) }
            binding.taskStatus.setOnCheckedChangeListener { buttonView, isChecked ->
                if(toDoTask.isFinished != isChecked){
                    toDoTask.isFinished = isChecked
                    toDoTaskClickListener.onCheckboxChangeToDoTaskStatus(toDoTask)
                }

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

