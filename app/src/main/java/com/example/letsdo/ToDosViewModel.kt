package com.example.letsdo

import android.app.Application
import androidx.lifecycle.*
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDo
import com.example.letsdo.data.ToDoDAO

class ToDosViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private var toDoDAO: ToDoDAO

    var toDosQuantity: LiveData<Int>

    var toDos: LiveData<List<ToDo>>

    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
        toDosQuantity = toDoDAO.count()
        toDos = toDoDAO.getAll()
    }



}
