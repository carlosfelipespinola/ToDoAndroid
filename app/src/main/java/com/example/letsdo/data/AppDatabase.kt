package com.example.letsdo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 2, entities = [ToDo::class, ToDoTask::class], exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val toDoDAO: ToDoDAO
    abstract val toDoTaskDoDAO: ToDoTaskDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance = INSTANCE
                val dbName = "todo_database"

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        dbName
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}