package com.example.letsdo

import android.app.Application
import androidx.lifecycle.*
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDoDAO

class ToDosViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private var toDoDAO: ToDoDAO
    private var _toDosQuantity: LiveData<Int>
    private var _toDosQuantityString: LiveData<String>
    val toDosQuantityStr: LiveData<String>
        get() = _toDosQuantityString

    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
        _toDosQuantity = toDoDAO.count()
        _toDosQuantityString = Transformations.map(_toDosQuantity){ "${it} itens cadastrados" }
    }



}
