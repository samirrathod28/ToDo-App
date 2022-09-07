package com.example.todoapp.room

import androidx.lifecycle.LiveData

class toDoRepo(private val toDoDao: toDoDao) {

    val allToDo : LiveData<List<toDoTable>> = toDoDao.getAllToDo()

    suspend fun insert(toDoTable: toDoTable){
        toDoDao.insert(toDoTable)
    }

    suspend fun delete(toDoTable: toDoTable){
        toDoDao.delete(toDoTable)
    }

    suspend fun update(toDoTable: toDoTable){
        toDoDao.update(toDoTable)
    }

}