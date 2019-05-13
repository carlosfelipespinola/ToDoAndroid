package com.example.letsdo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDoTask
import com.example.letsdo.data.ToDoTaskDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoTaskEditorViewModel(application: Application, private val toDoUid: Long) : AndroidViewModel(application) {
    private var toDoTaskDAO: ToDoTaskDAO

    var taskName: MutableLiveData<String> = MutableLiveData()
    var taskStatus: MutableLiveData<Boolean> = MutableLiveData()
    var taskLoaded: ToDoTask? = null


    init {
        val db = AppDatabase.getInstance(application)
        toDoTaskDAO = db.toDoTaskDoDAO
        taskName.value = ""
        taskStatus.value = false
    }

    fun loadExistingToDoTask(uid: Long){
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = toDoTaskDAO.getByUidSync(uid)
            if(toDoTask != null){
                taskName.postValue(toDoTask.name)
                taskStatus.postValue(toDoTask.isFinished)
                taskLoaded = toDoTask
            }
        }
    }

    fun insertOrUpdateToDoTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val taskCreatedFromInputValues = createNewTaskFromMutableLiveDataValues()
            if(isInEditMode()){
                updateToDoTask(oldToDoTask = taskLoaded!!, newToDoTask = taskCreatedFromInputValues)
            }else{
                insertToDoTask(taskCreatedFromInputValues)
            }
        }
    }

    private fun createNewTaskFromMutableLiveDataValues(): ToDoTask {
        val task = ToDoTask(taskName.value!!, toDoUid)
        task.isFinished = taskStatus.value!!
        return task
    }

    private fun isInEditMode(): Boolean{
        return taskLoaded != null
    }

    private fun insertToDoTask(toDoTask: ToDoTask){
        viewModelScope.launch(Dispatchers.IO){
            toDoTaskDAO.insert(toDoTask)
        }
    }

    private fun updateToDoTask(oldToDoTask: ToDoTask, newToDoTask: ToDoTask){
        viewModelScope.launch(Dispatchers.IO){
            newToDoTask.uid = oldToDoTask.uid
            toDoTaskDAO.update(newToDoTask)
        }
    }

    fun deleteToDoTask(){
        if(taskLoaded == null){
            return
        }
        viewModelScope.launch(Dispatchers.IO){
            toDoTaskDAO.delete(taskLoaded!!)
        }
    }



}
