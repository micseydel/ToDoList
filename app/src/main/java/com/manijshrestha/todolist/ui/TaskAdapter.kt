package com.manijshrestha.todolist.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater.from
import android.view.ViewGroup
import android.widget.CheckBox
import com.manijshrestha.todolist.R
import com.manijshrestha.todolist.data.Task
import com.manijshrestha.todolist.data.TaskDao
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TaskAdapter(private var tasks: List<Task>, private val taskDao: TaskDao) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TaskAdapter.TaskViewHolder {
        return TaskViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: TaskAdapter.TaskViewHolder, position: Int) {
        viewHolder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(from(parent.context).inflate(R.layout.item_to_do, parent, false)) {

        private val compositeDisposable = CompositeDisposable()

        fun bind(task: Task) = with(itemView) {
            val taskCb = findViewById<CheckBox>(R.id.task_cb)
            taskCb.text = task.description
            taskCb.isChecked = task.completed

            taskCb.setOnCheckedChangeListener { buttonView, isChecked ->
                task.completed = isChecked

                compositeDisposable.add(Observable.fromCallable { taskDao.updateTask(task) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())
            }
        }
    }
}