package com.example.todoapp.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.RvAdaper
import com.example.todoapp.adapter.ToDoClickInterface
import com.example.todoapp.adapter.TodoCLickDeleteInterface
import com.example.todoapp.databinding.ActivityToDoBinding
import com.example.todoapp.room.toDoTable
import com.example.todoapp.room.toDoViewModel

class ToDoActivity : AppCompatActivity(), ToDoClickInterface, TodoCLickDeleteInterface {

    private lateinit var binding: ActivityToDoBinding
    lateinit var viewModel: toDoViewModel
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences=getSharedPreferences("user_session", MODE_PRIVATE)
        binding.rvToDo.layoutManager =LinearLayoutManager(this)

        val toDoRvAdaper = RvAdaper(this,this,this)
        binding.rvToDo.adapter = toDoRvAdaper
        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(toDoViewModel::class.java)
        viewModel.allToDo.observe(this, Observer { list->
            list?.let {
                toDoRvAdaper.updateList(it)
            }

        })
        binding.FabAddTodo.setOnClickListener(){
            val intent = Intent(this,AddEditToDoActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onBackPressed() {
        var alert= AlertDialog.Builder(this)
        alert.setTitle( "Want to Exit?")
        alert.setPositiveButton("YES",{
                dialogInterface: DialogInterface, i: Int ->
            finishAffinity()

        })
        alert.setNegativeButton("No",{
                dialogInterface: DialogInterface, i: Int ->

            dialogInterface.cancel()

        })
        alert.show()

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
    override fun onToDoClick(toDoTable: toDoTable) {
        val intent = Intent(this,AddEditToDoActivity::class.java)
        intent.putExtra("toDoType","Edit")
        intent.putExtra("toDoTitle", toDoTable.toDoTitle)
        intent.putExtra("toDoDescription",toDoTable.toDoDescription)
        intent.putExtra("toDoID",toDoTable.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(toDoTable: toDoTable) {

        viewModel.deleteToDo(toDoTable)
        Toast.makeText(applicationContext,"${toDoTable.toDoTitle} Deleted",Toast.LENGTH_LONG).show()
    }
}