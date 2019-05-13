package com.example.letsdo

import android.app.Application
import androidx.lifecycle.*
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDo
import com.example.letsdo.data.ToDoDAO
import kotlinx.coroutines.*

class CreateToDoViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    var newToDo = ToDo(name = "")
    private var toDoDAO: ToDoDAO

    private val _onToDoInserted :MutableLiveData<Long> = MutableLiveData()
    val onToDoInserted: LiveData<Long>
        get() = _onToDoInserted

    private val _onToDoInsertError: MutableLiveData<Exception> = MutableLiveData()
    val onToDoInsertError: LiveData<Exception>
        get() = _onToDoInsertError


    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
    }

    fun insertToDo(){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val uid = toDoDAO.insert(newToDo)
                newToDo.uid = uid
                _onToDoInserted.postValue(uid)
            }catch (e: Exception){
                _onToDoInsertError.postValue(e)
            }

        }
    }


}
