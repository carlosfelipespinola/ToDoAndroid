package com.example.letsdo

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.letsdo.data.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ToDoTaskCRUDTest {

    private lateinit var toDoTaskDAO: ToDoTaskDAO
    private lateinit var toDoDAO: ToDoDAO
    private lateinit var db: AppDatabase

    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        toDoTaskDAO = db.toDoTaskDoDAO
        toDoDAO = db.toDoDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun testCrud(){
        assert(createToDoTask())
        assert(updateSomeToDoTask())
        assert(getToDoTasks())
        assert(deleteSomeToDoTasks())
        assert(cascadeDeleteToDoTasks())
    }

    fun createToDoTask(): Boolean{
        val toDo = ToDo(name = "Teste")
        toDo.uid = 1L
        val createdToDoUid = toDoDAO.insert(toDo)
        val createdToDoTaskUid = toDoTaskDAO.insert(ToDoTask("Task", createdToDoUid))
        return createdToDoTaskUid > 0
    }

    private fun updateSomeToDoTask(): Boolean{
        val toDoTask = toDoTaskDAO.getByUidSync(1L)
        if(toDoTask == null){
            return false
        }
        toDoTask.name = "newName"
        toDoTaskDAO.update(toDoTask)
        return true
    }

    private fun getToDoTasks(): Boolean{
        val listOfToDoTasks = toDoTaskDAO.getAllTasksFromToDo(1L).value
        if(listOfToDoTasks == null){
            return false
        }
        return listOfToDoTasks.size > 0
    }

    private fun deleteSomeToDoTasks(): Boolean{
        val toDoTask = toDoTaskDAO.getByUidSync(1L)
        if(toDoTask == null){
            return false
        }

        toDoTaskDAO.delete(toDoTask)
        return true
    }

    private fun cascadeDeleteToDoTasks(): Boolean{
        val toDo = ToDo(name = "Teste")
        toDo.uid = 100L
        val createdToDoUid = toDoDAO.insert(toDo)
        toDoTaskDAO.insert(ToDoTask("Task", createdToDoUid))
        toDoTaskDAO.insert(ToDoTask("Task", createdToDoUid))
        toDoTaskDAO.insert(ToDoTask("Task", createdToDoUid))
        toDoDAO.delete(toDo)
        return toDoTaskDAO.getAllTasksFromToDo(toDo.uid).value.isNullOrEmpty()
    }
}