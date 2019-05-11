package com.example.letsdo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToDoTaskEditorViewModelFactory(private val application: Application, private val toDoUid: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToDoTaskEditorViewModel::class.java)){
            return ToDoTaskEditorViewModel(application, toDoUid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}