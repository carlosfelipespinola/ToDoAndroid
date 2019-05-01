package com.example.letsdo

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDo
import com.example.letsdo.data.ToDoDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private var toDoDAO: ToDoDAO
    private var toDoUid: Long = -1L
    val lodedToDoUid: Long
        get() = toDoUid

    lateinit var toDo: LiveData<ToDo>

    private val _onToDoDeleted = MutableLiveData<Unit>()
    val onToDoDeleted: LiveData<Unit>
        get() = _onToDoDeleted

    private val _onToDoDeleteError = MutableLiveData<Exception>()
    val onToDoDeleteError: LiveData<Exception>
        get() = _onToDoDeleteError


    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
    }

    fun setToDoUid(toDoUid: Long){
        this.toDoUid = toDoUid
        toDo = toDoDAO.getByUid(toDoUid)
    }

    fun deleteToDo(){
        viewModelScope.launch(Dispatchers.IO) {
            if(toDo.value == null){
                _onToDoDeleteError.postValue(Exception("Can't delete To-do list"))
                this.cancel()
            }

            try {
                toDoDAO.delete(toDo.value!!)
                _onToDoDeleted.postValue(Unit)
            }catch (e: Exception){
                _onToDoDeleteError.postValue(e)
            }

        }

    }


}
