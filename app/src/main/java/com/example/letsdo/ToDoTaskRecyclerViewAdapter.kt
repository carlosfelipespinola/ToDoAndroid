package com.example.letsdo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdo.data.ToDoTask

interface ToDoTaskClickListener {
    fun onClick(toDoTask: ToDoTask)
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

    class ToDoTaskViewHolder private constructor(private val view: View): RecyclerView.ViewHolder(view){

        private val textView: TextView = view.findViewById(R.id.tv_task)
        fun bind(toDoTask: ToDoTask, toDoTaskClickListener: ToDoTaskClickListener){
            textView.text = toDoTask.name
            view.setOnClickListener { toDoTaskClickListener.onClick(toDoTask) }
        }


        companion object {
            fun from(parent: ViewGroup): ToDoTaskViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.to_do_task_item, parent, false)
                return ToDoTaskViewHolder(view)
            }
        }
    }

}

