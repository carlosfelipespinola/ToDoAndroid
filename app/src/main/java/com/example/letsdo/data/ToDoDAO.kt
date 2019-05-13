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
    
    @Query("SELECT todo.*, round((count(case tasks.is_finished when 1 then 1.0 else null end)/(count(*) * 1.00)) * 100) as percentage from to_dos_table as todo LEFT JOIN to_do_task_table as tasks on todo.uid = tasks.to_do_uid group by todo.uid")
    fun getAll(): LiveData<List<ToDo>>

    @Query("SELECT * FROM to_dos_table WHERE uid IS :uid")
    fun getByUid(uid: Long): LiveData<ToDo>

    @Query("SELECT COUNT(*) FROM to_dos_table ")
    fun count(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM to_do_task_table WHERE to_do_uid = :todoUid")
    fun countTasksInToDo(todoUid: Long): LiveData<Int>

    @Query("SELECT round((count(case is_finished when 1 then 1.0 else null end)/(count(*) * 1.00)) * 100) as percentage FROM to_do_task_table WHERE to_do_uid = :todoUid")
    fun countCompletedTasksPercentage(todoUid: Long): LiveData<Int>
}