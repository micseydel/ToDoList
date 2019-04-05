package com.manijshrestha.todolist.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.*
import android.view.ViewGroup
import android.widget.CheckBox
import com.manijshrestha.todolist.R
import com.manijshrestha.todolist.data.Task

class TaskAdapter(private var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TaskAdapter.TaskViewHolder {
        return TaskViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: TaskAdapter.TaskViewHolder, position: Int) {
        viewHolder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(from(parent.context).inflate(R.layout.item_to_do, parent, false)) {

        fun bind(task: Task) = with(itemView) {
            val taskCb = findViewById<CheckBox>(R.id.task_cb)
            taskCb.text = task.description
            taskCb.isChecked = task.completed
        }
    }
}