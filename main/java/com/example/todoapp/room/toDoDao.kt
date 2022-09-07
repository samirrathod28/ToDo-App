package com.example.todoapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface toDoDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(toDoTable: toDoTable)

    @Delete
    suspend fun delete(toDoTable: toDoTable)

    @Query("Select * from ToDoTable order by id ASC")
    fun getAllToDo(): LiveData<List<toDoTable>>

    @Update
    suspend fun update(toDoTable: toDoTable)
}