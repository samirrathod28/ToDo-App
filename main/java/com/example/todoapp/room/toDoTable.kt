package com.example.todoapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ToDoTable")

// on below line we are specifying our column info
// and inside that we are passing our column name
class toDoTable (@ColumnInfo(name = "title")val toDoTitle :String, @ColumnInfo(name = "description")val toDoDescription :String, @ColumnInfo(name = "date and time")val dat :String) {
    // on below line we are specifying our key and
    // then auto generate as true and we are
    // specifying its initial value as 0
    @PrimaryKey(autoGenerate = true) var id = 0
}