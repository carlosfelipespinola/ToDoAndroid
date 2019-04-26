package com.example.letsdo.interfaces

import android.view.View
import com.example.letsdo.data.ToDo

interface OnToDoItemClickListener {
    fun onToDoItemClick(v: View?, toDo: ToDo)
}