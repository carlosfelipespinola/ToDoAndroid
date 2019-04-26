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

@RunWith(AndroidJUnit4::class)
class DatabaseUniqueConstraintsTest {

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
    fun testUniqueConstraintsValidation(){
        assert(uniqueConstraintsValidationIsBeingDone())
    }

    private fun uniqueConstraintsValidationIsBeingDone(): Boolean{
        try {
            insertTwoToDoWithUniqueViolationConstraints()
        }catch(e: SQLiteConstraintException){
            return true
        }

        return false
    }

    @Throws(SQLiteConstraintException::class)
    private fun insertTwoToDoWithUniqueViolationConstraints(){
        var toDoA = ToDo(name = "Lista 1")
        var toDoB = ToDo(name = "Lista 2")
        val uid = 2000L
        toDoA.uid = uid
        toDoB.uid = uid
        toDoDAO.insert(toDoA)
        toDoDAO.insert(toDoB)
    }
}