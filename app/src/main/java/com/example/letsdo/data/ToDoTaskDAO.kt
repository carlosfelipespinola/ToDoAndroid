package com.example.letsdo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoTaskDAO {
    @Insert
    fun insert(toDoTask: ToDoTask): Long

    @Update
    fun update(toDoTask: ToDoTask)

    @Delete
    fun delete(toDoTask: ToDoTask)

    @Query("select * from to_do_task_table where to_do_uid = :toDoUid")
    fun getAllTasksFromToDo(toDoUid: Long): LiveData<List<ToDoTask>>

    @Query("select * from to_do_task_table where to_do_uid = :toDoUid and name like :search")
    fun getTasksFromToDoWhere(toDoUid: Long, search: String): LiveData<List<ToDoTask>>

    @Query("SELECT * FROM to_do_task_table WHERE uid = :uid")
    fun getByUidSync(uid: Long): ToDoTask
}