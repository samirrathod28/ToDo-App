package com.example.todoapp.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class toDoViewModel(application: Application):AndroidViewModel(application) {

    val allToDo : LiveData<List<toDoTable>>
    val reposetory: toDoRepo

    init {
        val dao = toDoDb.getDatabase(application).getToDoDao()
        reposetory = toDoRepo(dao)
        allToDo = reposetory.allToDo
    }

    fun deleteToDo(toDoTable: toDoTable) = viewModelScope.launch(Dispatchers.IO) {
        reposetory.delete(toDoTable)
    }

    fun updateToDo(toDoTable: toDoTable) = viewModelScope.launch(Dispatchers.IO) {
        reposetory.update(toDoTable)
    }

    fun addToDo(toDoTable: toDoTable) = viewModelScope.launch(Dispatchers.IO) {
        reposetory.insert(toDoTable)
    }
}