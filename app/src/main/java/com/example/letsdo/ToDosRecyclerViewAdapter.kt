package com.example.letsdo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.letsdo.data.ToDo
import com.example.letsdo.databinding.TodoItemRowBinding

class ToDosRecyclerViewAdapter( toDos: List<ToDo>): RecyclerView.Adapter<ToDosRecyclerViewAdapter.ViewHolder>() {
    private var _toDos: List<ToDo> = toDos
    var toDos
        set(value) {
            _toDos = value

            notifyDataSetChanged()
        }
        get() = _toDos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<TodoItemRowBinding>(
            LayoutInflater.from(
                parent.context),
                R.layout.todo_item_row,
                parent,
                false)

        return ViewHolder(binding)
    }

    inner class ViewHolder(private val itemRowBinding: TodoItemRowBinding): RecyclerView.ViewHolder(itemRowBinding.root) {

        fun bind(toDo: ToDo){
            itemRowBinding.todo = toDo
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDo = _toDos[position]
        holder.bind(toDo)
    }

    override fun getItemCount(): Int {
        return _toDos.size
    }

}