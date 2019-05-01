package com.example.letsdo

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.*
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDo
import com.example.letsdo.data.ToDoDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditToDoViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    lateinit var toDo: LiveData<ToDo>
    private var toDoDAO: ToDoDAO
    private var toDoUid: Long = -1L

    private val _onToDoUpdated : MutableLiveData<Long> = MutableLiveData()
    val onToDoUpdated: LiveData<Long>
        get() = _onToDoUpdated

    private val _onToDoUpdateError: MutableLiveData<Exception> = MutableLiveData()
    val onToDoUpdateError: LiveData<Exception>
        get() = _onToDoUpdateError


    init {
        val db = AppDatabase.getInstance(application)
        toDoDAO = db.toDoDAO
    }

    fun setToDoUid(toDoUid: Long){
        this.toDoUid = toDoUid
        toDo = toDoDAO.getByUid(toDoUid)
    }


    fun updateToDo(updatedToDo: ToDo){
        updatedToDo.uid = toDoUid
        viewModelScope.launch(Dispatchers.IO){
            try {
                toDoDAO.update(updatedToDo)
                _onToDoUpdated.postValue(toDoUid)
            }catch (e: Exception){
                _onToDoUpdateError.postValue(e)
            }
        }

    }


}
