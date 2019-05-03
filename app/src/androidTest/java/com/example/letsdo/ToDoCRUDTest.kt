package com.example.letsdo

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.letsdo.data.AppDatabase
import com.example.letsdo.data.ToDo
import com.example.letsdo.data.ToDoDAO
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.sql.SQLIntegrityConstraintViolationException


@RunWith(AndroidJUnit4::class)
class ToDoCRUDTest {
    private lateinit var toDoDAO: ToDoDAO
    private lateinit var db: AppDatabase

    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        toDoDAO = db.toDoDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun testCrud(){
        assert(createToDo())
        assert(createToDo())
        assert(getToDos())
        assert(updateSomeToDo())
        assert(deleteSomeToDo())
    }

    private fun createToDo(): Boolean{
        val createToDoUid = toDoDAO.insert(ToDo(name = "Teste"))
        return createToDoUid > 0
    }

    private fun getToDos(): Boolean{
        val listOfToDos = toDoDAO.getAll().value
        if(listOfToDos == null){
            return false
        }
        return listOfToDos.size > 0
    }

    private fun deleteSomeToDo(): Boolean{
        val toDo = toDoDAO.getByUid(1L).value
        if(toDo == null){
            return false
        }

        toDoDAO.delete(toDo)
        return true
    }

    private fun updateSomeToDo(): Boolean{
        val toDo = toDoDAO.getByUid(1L).value
        if(toDo == null){
            return false
        }
        toDo.name = "newName"
        toDoDAO.update(toDo)
        return true
    }

}