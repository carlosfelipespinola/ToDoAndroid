package com.example.letsdo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoTaskDAO {
    @Insert
    fun insert(toDoTask: ToDoTask): Long

    @Update
    fun update(toDoTask: ToDo)

    @Delete
    fun delete(toDoTask: ToDo)

    @Query("select * from to_do_task_table where to_do_uid = :toDoUid")
    fun getAllTasksFromToDo(toDoUid: Long): LiveData<List<ToDoTask>>
}