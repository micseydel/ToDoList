package com.manijshrestha.todolist.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import java.lang.RuntimeException


class TaskAdapter(private val tasks: MutableList<Task>, private val taskDao: TaskDao, private val activity: Activity) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TaskViewHolder {
        registerBroadcastReceiver()
        return TaskViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
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
                    removeFromViewAt(adapterPosition)

                    compositeDisposable.add(
                            Observable.fromCallable {
                                taskDao.deleteTask(task)
                            }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe())
                }

                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.no)) { _, _ ->
                    // just create the button, it doesn't need to do anything
                    // (default behavior dismisses the dialog on press)
                }

                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, activity.getString(R.string.snooze)) { _, _ ->
                    val fragment = DatePickerFragment()

                    val bundle = Bundle()
                    bundle.putInt("adapterPosition", adapterPosition)
                    bundle.putString("description", tasks[adapterPosition].description)
                    bundle.putLong("notificationId", tasks[adapterPosition].id)
                    fragment.arguments = bundle

                    fragment.show((activity as FragmentActivity).supportFragmentManager, "timepickerstart")
                }

                dialog.show()

                true
            }
        }
    }

    private fun registerBroadcastReceiver() {
        Log.e("canary", "registering...")
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // FIXME: don't want these default arguments; else will have to check them
                val adapterPosition = intent.getIntExtra("adapterPosition", -1)

                val task = tasks[adapterPosition]

                task.snoozedTo = intent.getLongExtra("instantMillis", -1)
                // FIXME: do we need this song and dance here? (probably)
                compositeDisposable.add(Observable.fromCallable { taskDao.updateTask(task) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())

                removeFromViewAt(adapterPosition)
            }
        }
        activity.applicationContext.registerReceiver(broadcastReceiver, IntentFilter("com.an.sms.example2"))
    }

    private fun removeFromViewAt(adapterPosition: Int) {
        tasks.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition, tasks.size)
    }
}