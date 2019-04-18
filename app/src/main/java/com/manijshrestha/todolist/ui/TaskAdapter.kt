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
import android.widget.LinearLayout
import android.widget.EditText


class TaskAdapter(private val tasks: MutableList<Task>, private val taskDao: TaskDao, private val activity: Activity) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

    init {
        registerBroadcastReceiver()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): TaskViewHolder {
        return TaskViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        viewHolder.bind(tasks[position])
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(from(parent.context).inflate(R.layout.item_to_do, parent, false)) {

        private val compositeDisposable = CompositeDisposable()

        fun bind(task: Task) = with(itemView) {
            val taskCheckbox = findViewById<CheckBox>(R.id.task_checkbox)
            taskCheckbox.text = task.description
            taskCheckbox.isChecked = task.completed

            taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                task.completed = isChecked

                compositeDisposable.add(Observable.fromCallable { taskDao.updateTask(task) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())
            }

            setTaskCheckboxOnClickListener(taskCheckbox, task, adapterPosition)
        }
    }

    private fun registerBroadcastReceiver() {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val adapterPosition = intent.getIntExtra(ToDoListNotificationPublisher.ADAPTER_POSITION, -1)

                val task = tasks[adapterPosition]

                task.snoozedTo = intent.getLongExtra(ToDoListNotificationPublisher.INSTANT_MILLIS, -1)
                compositeDisposable.add(Observable.fromCallable { taskDao.updateTask(task) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())

                removeFromViewAt(adapterPosition)
            }
        }
        activity.applicationContext.registerReceiver(broadcastReceiver, IntentFilter(TimePickerFragment.INTENT_ACTION))
    }

    private fun removeFromViewAt(adapterPosition: Int) {
        tasks.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition, tasks.size)
    }

    private fun setTaskCheckboxOnClickListener(taskCheckbox: CheckBox, task: Task, adapterPosition: Int) {
        taskCheckbox.setOnLongClickListener {
            val dialog = AlertDialog.Builder(activity).create()
            dialog.setTitle(activity.getString(R.string.edit_action_question))
            dialog.setMessage(
                    activity.getString(R.string.tap_outside_to_cancel)
                            .format(
                                    if (task.completed) activity.getString(R.string.done) else activity.getString(R.string.incomplete),
                                    task.description
                            )
            )

            dialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.delete)) { _, _ ->
                removeFromViewAt(adapterPosition)

                compositeDisposable.add(
                        Observable.fromCallable {
                            taskDao.deleteTask(task)
                        }
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe())
            }

            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, activity.getString(R.string.snooze)) { _, _ ->
                val fragment = DatePickerFragment()

                val bundle = Bundle()
                bundle.putInt(ToDoListNotificationPublisher.ADAPTER_POSITION, adapterPosition)
                bundle.putString(ToDoListNotificationPublisher.DESCRIPTION, tasks[adapterPosition].description)
                bundle.putLong(ToDoListNotificationPublisher.NOTIFICATIONID, tasks[adapterPosition].id)
                fragment.arguments = bundle

                fragment.show((activity as FragmentActivity).supportFragmentManager, TAG)
            }

            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.edit)) { _, _ ->
                val alertDialog = AlertDialog.Builder(activity)
                alertDialog.setTitle(activity.getString(R.string.edit_alert_content))

                val input = EditText(activity)
                input.setPadding(50, 0, 0, 30)
                input.setText(task.description)
                val lp = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
                input.layoutParams = lp
                alertDialog.setView(input)

                alertDialog.setPositiveButton(R.string.confirm) { dialog, which ->
                    taskCheckbox.text = input.text
                    compositeDisposable.add(
                            Observable.fromCallable {
                                task.description = input.text.toString()
                                taskDao.updateTask(task)
                            }
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe())
                }

                alertDialog.show()
            }

            dialog.show()

            true
        }
    }

    companion object {
        const val TAG = "timepickerstart"
    }
}