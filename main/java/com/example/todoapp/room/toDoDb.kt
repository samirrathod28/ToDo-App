package com.example.todoapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [toDoTable::class], version = 1, exportSchema = false)
abstract class toDoDb : RoomDatabase() {

    abstract fun getToDoDao() : toDoDao

    companion object{

        @Volatile
        private var INSTANCE:toDoDb? = null

        fun getDatabase(context: Context):toDoDb{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    toDoDb::class.java,
                    "todo_database").build()
                INSTANCE = instance
                instance
            }
        }

    }
    
}