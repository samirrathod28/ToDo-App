package com.example.todoapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.room.toDoTable

class RvAdaper(val context: Context,
               val toDoClickInterface: ToDoClickInterface,
               val todoCLickDeleteInterface: TodoCLickDeleteInterface
               ): RecyclerView.Adapter<RvAdaper.viewHolder>() {

    private val allToDO = arrayListOf<toDoTable>()

    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val toDoTitleTv = itemView.findViewById<TextView>(R.id.toDoTitleTv)
        val toDoTimeTv = itemView.findViewById<TextView>(R.id.tvTimeDate)
        val deleteIcon = itemView.findViewById<ImageView>(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_design,parent,false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       holder.toDoTitleTv.setText(allToDO.get(position).toDoTitle)
        holder.toDoTimeTv.setText("Updated: "+allToDO.get(position).dat)
        holder.deleteIcon.setOnClickListener {
            todoCLickDeleteInterface.onDeleteIconClick(allToDO.get(position))
        }
        holder.itemView.setOnClickListener {
            toDoClickInterface.onToDoClick(allToDO.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allToDO.size
    }

    fun updateList(newList: List<toDoTable>){

        allToDO.clear()
        allToDO.addAll(newList)
        notifyDataSetChanged()


    }


}
interface TodoCLickDeleteInterface{
    fun onDeleteIconClick(toDoTable: toDoTable)
}
interface ToDoClickInterface{
    fun onToDoClick(toDoTable: toDoTable)
}