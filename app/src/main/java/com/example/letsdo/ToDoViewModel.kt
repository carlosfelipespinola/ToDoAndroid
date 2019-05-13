package com.example.letsdo

import android.app.Application
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.example.letsdo.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class ToDoViewModel(application: Application, toDoUid: Long) : AndroidViewModel(application) {
    private var toDoTaskDAO: ToDoTaskDAO
    private var toDoDAO: ToDoDAO
    private var toDoUid: Long = -1L
    val lodedToDoUid: Long
        get() = toDoUid

    var toDo: LiveData<ToDo>

    private val _onToDoDeleted = MutableLiveData<Unit>()
    val onToDoDeleted: LiveData<Unit>
        get() = _onToDoDeleted

    private val _onToDoDeleteError = MutableLiveData<Exception>()
    val onToDoDeleteError: LiveData<Exception>
        get() = _onToDoDeleteError

    var toDoTasksCount: LiveData<Int> = MutableLiveData()
    var toDoTasks: LiveData<List<ToDoTask>>
    var completedToDoTasksPercentage: LiveData<Int>
    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
        toDoTaskDAO = db.toDoTaskDoDAO
        this.toDoUid = toDoUid
        toDo = toDoDAO.getByUid(toDoUid)
        toDoTasksCount = toDoDAO.countTasksInToDo(todoUid = this.toDoUid)
        toDoTasks = toDoTaskDAO.getAllTasksFromToDo(this.toDoUid)
        completedToDoTasksPercentage = toDoDAO.countCompletedTasksPercentage(toDoUid)
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

    fun updateToDoTask(toDoTask: ToDoTask){
        viewModelScope.launch(Dispatchers.IO){
            toDoTaskDAO.update(toDoTask)
        }
    }
}
