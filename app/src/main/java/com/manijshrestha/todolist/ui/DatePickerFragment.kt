package com.manijshrestha.todolist.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.widget.DatePicker
import android.widget.Toast
import com.manijshrestha.todolist.data.Task
import com.manijshrestha.todolist.data.TaskDao
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

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
        val toCheck = Calendar.getInstance()
        toCheck.set(Calendar.YEAR, year)
        toCheck.set(Calendar.MONTH, month)
        toCheck.set(Calendar.DAY_OF_MONTH, day)

        val now = Calendar.getInstance()

        if (toCheck.before(now)) {
            Toast.makeText(
                    context,
                    "Must select a date in the future!", // FIXME: factor out into strings!
                    Toast.LENGTH_SHORT
            ).show()

            val fragment = DatePickerFragment()
            fragment.arguments = arguments
            fragment.show(fragmentManager, tag)
        } else {
            val fragment = TimePickerFragment()

            arguments!!.putInt("year", year) // FIXME: factor out strings into companion
            arguments!!.putInt("month", month)
            arguments!!.putInt("day", day)
            fragment.arguments = arguments

            fragment.show((activity as FragmentActivity).supportFragmentManager, "timepickerfinish") // FIXME: factor out into companion
        }
    }
}
