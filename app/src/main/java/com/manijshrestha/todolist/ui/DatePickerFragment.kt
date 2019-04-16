package com.manijshrestha.todolist.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.widget.DatePicker
import com.manijshrestha.todolist.data.Task
import com.manijshrestha.todolist.data.TaskDao
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var taskDao: TaskDao
    // FIXME: give a more meaningful name
    lateinit var f: (Int) -> (Unit)
    lateinit var task: Task

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // FIXME: figure out this warning
        // code from https://developer.android.com/guide/topics/ui/controls/pickers.html
        return DatePickerDialog(activity, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // FIXME: verify not in the past
        val fragment = TimePickerFragment()
        fragment.taskDao = taskDao
        fragment.task = task
        fragment.f = f

        arguments!!.putInt("year", year)
        arguments!!.putInt("month", month)
        arguments!!.putInt("day", day)
        fragment.arguments = arguments

        fragment.show((activity as FragmentActivity).supportFragmentManager, "timepickerfinish")
    }
}
