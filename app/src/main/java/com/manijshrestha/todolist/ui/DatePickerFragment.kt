package com.manijshrestha.todolist.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.widget.DatePicker
import android.widget.Toast
import com.manijshrestha.todolist.R
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // I'm really not sure what to do about this warning
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
                    context!!.getString(R.string.must_select_a_date_in_the_future),
                    Toast.LENGTH_SHORT
            ).show()

            val fragment = DatePickerFragment()
            fragment.arguments = arguments
            fragment.show(fragmentManager, tag)
        } else {
            val fragment = TimePickerFragment()

            arguments!!.putInt(ToDoListNotificationPublisher.YEAR, year)
            arguments!!.putInt(ToDoListNotificationPublisher.MONTH, month)
            arguments!!.putInt(ToDoListNotificationPublisher.DAY, day)
            fragment.arguments = arguments

            fragment.show((activity as FragmentActivity).supportFragmentManager, TAG)
        }
    }

    companion object {
        const val TAG = "timepickerfinish"
    }
}
