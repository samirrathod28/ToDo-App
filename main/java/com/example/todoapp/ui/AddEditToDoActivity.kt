package com.example.todoapp.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityAddEditToDoBinding
import com.example.todoapp.room.toDoTable
import com.example.todoapp.room.toDoViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditToDoBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var viewModel: toDoViewModel
    var toDoId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditToDoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences=getSharedPreferences("user_session", MODE_PRIVATE)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))[toDoViewModel::class.java]

        val toDoType = intent.getStringExtra("toDoType")
        if (toDoType.equals("Edit")) {
            val toDoTitle = intent.getStringExtra("toDoTitle")
            val toDoDescription = intent.getStringExtra("toDoDescription")
            toDoId = intent.getIntExtra("toDoID", -1)
            binding.AddUpdate.text = "Update"
            binding.etTitle.setText(toDoTitle)
            binding.etDescription.setText(toDoDescription)
        } else {
            binding.AddUpdate.text = "Create"
        }

        binding.AddUpdate.setOnClickListener() {
            val todoTitle = binding.etTitle.text.toString()
            val todoDesc = binding.etDescription.text.toString()

            if (toDoType.equals("Edit")) {
                if (todoTitle.isNotEmpty() && todoDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat(getString(R.string.dateandtimeformat))
                    val currentDate: String = sdf.format(Date())
                    val updateTodo = toDoTable(todoTitle, todoDesc, currentDate,)
                    updateTodo.id = toDoId
                    viewModel.updateToDo(updateTodo)
                    Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show()
                }

            } else {
                if (todoTitle.isNotEmpty() && todoDesc.isNotEmpty()) {
                    val sdf = SimpleDateFormat(getString(R.string.dateandtimeformat))
                    val currentDate: String = sdf.format(Date())
                    viewModel.addToDo(toDoTable(todoTitle, todoDesc, currentDate))
                    Toast.makeText(this, "Created", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(this,ToDoActivity::class.java))
            this.finish()

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean
    {
        menuInflater.inflate(R.menu.option_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.logout->
            {
                sharedPreferences.edit().clear().apply()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,ToDoActivity::class.java))
        this.finish()
    }
}