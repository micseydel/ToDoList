package com.manijshrestha.todolist.ui

import android.app.Activity
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
import android.app.AlertDialog
import android.content.DialogInterface


class TaskAdapter(private val tasks: MutableList<Task>, private val taskDao: TaskDao, private val activity: Activity) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

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

            taskCb.setOnCheckedChangeListener { _, isChecked ->
                task.completed = isChecked

                compositeDisposable.add(Observable.fromCallable { taskDao.updateTask(task) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())
            }

            taskCb.setOnLongClickListener {
                val dialog = AlertDialog.Builder(activity).create()
                dialog.setTitle(activity.getString(R.string.delete_question))
                dialog.setMessage(
                        activity.getString(R.string.delete_question_long)
                                .format(
                                        if (task.completed) activity.getString(R.string.done) else activity.getString(R.string.incomplete),
                                        task.description
                                )
                )

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.yes)) { _, _ ->
                    tasks.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    notifyItemRangeChanged(adapterPosition, tasks.size)

                    compositeDisposable.add(Observable.fromCallable {
                        taskDao.deleteTask(task)
                    }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe())
                }

                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.no)) { _, _ ->
                    // we intentionally do nothing in this case
                }

                dialog.show()

                true
            }
        }
    }
}