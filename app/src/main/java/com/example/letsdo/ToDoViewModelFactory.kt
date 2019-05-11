package com.example.letsdo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToDoViewModelFactory(private val application: Application, private val toDoUid: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ToDoViewModel::class.java)){
            return ToDoViewModel(application, toDoUid) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}