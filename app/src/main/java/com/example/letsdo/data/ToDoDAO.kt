package com.example.letsdo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDAO {

    @Insert
    fun insert(toDo: ToDo): Long

    @Update
    fun update(toDo: ToDo)

    @Delete
    fun delete(toDo: ToDo)

    @Query("SELECT * FROM to_dos_table")
    fun getAll(): LiveData<List<ToDo>>

    @Query("SELECT * FROM to_dos_table WHERE uid IS :uid")
    fun getByUid(uid: Long): LiveData<ToDo>

    @Query("SELECT COUNT(*) FROM to_dos_table ")
    fun count(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM to_do_task_table WHERE to_do_uid = :todoUid")
    fun countTasksInToDo(todoUid: Long): LiveData<Int>
}