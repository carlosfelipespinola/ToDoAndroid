package com.example.letsdo

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDo
import com.example.letsdo.data.ToDoDAO

class ToDoEditorViewModel(application: Application) : AndroidViewModel(application) {

    private var toDoDAO: ToDoDAO

    lateinit var toDo: LiveData<ToDo>

    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
    }

    fun setToDoUid(toDoUid: Long){
        toDo = toDoDAO.getByUid(toDoUid)
    }
    // TODO: Implement the ViewModel
}
