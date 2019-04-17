package com.manijshrestha.todolist.ui

import android.app.AlarmManager
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.RingtoneManager
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.DialogFragment
import android.support.v4.app.NotificationCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.format.DateFormat
import android.widget.TimePicker
import android.widget.Toast
import com.manijshrestha.todolist.R
import java.util.Calendar


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var description: String? = null
    private var notificationId: Long = -1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        description = arguments!!.getString(ToDoListNotificationPublisher.DESCRIPTION)
        notificationId = arguments!!.getLong(ToDoListNotificationPublisher.NOTIFICATIONID)

        // TODO: handle midnight properly here; switch +1hour to +1minute
        return TimePickerDialog(activity, this, hour, minute + 1, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {

        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, arguments!!.getInt(ToDoListNotificationPublisher.YEAR))
        c.set(Calendar.MONTH, arguments!!.getInt(ToDoListNotificationPublisher.MONTH))
        c.set(Calendar.DAY_OF_MONTH, arguments!!.getInt(ToDoListNotificationPublisher.DAY))
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0) // don't wait into the minute

        val now = Calendar.getInstance()

        if (c.before(now)) {
            Toast.makeText(
                    context,
                    context!!.getString(R.string.must_select_a_time_in_the_future),
                    Toast.LENGTH_SHORT
            ).show()

            val fragment = TimePickerFragment()
            fragment.arguments = arguments
            fragment.show(fragmentManager, tag)
        } else {
            val instantMillis = c.toInstant().toEpochMilli()
            Intent().also { newIntent ->
                newIntent.action = INTENT_ACTION

                newIntent.putExtra(ToDoListNotificationPublisher.ADAPTER_POSITION, arguments!!.getInt(ToDoListNotificationPublisher.ADAPTER_POSITION))
                newIntent.putExtra(ToDoListNotificationPublisher.INSTANT_MILLIS, instantMillis)

                context?.sendBroadcast(newIntent)
            }

            scheduleNotification(context!!, c.timeInMillis - Calendar.getInstance().timeInMillis)
        }
    }

    private fun scheduleNotification(context: Context, delayMillis: Long) {
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "everythingfornow";
        val channel = NotificationChannel(channelId,
                "The single channel I have for now", NotificationManager.IMPORTANCE_DEFAULT)
        mNotificationManager.createNotificationChannel(channel);

        val builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle(context.getString(R.string.reminder))
                .setContentText(description)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.equalsd)
                .setLargeIcon((ResourcesCompat.getDrawable(resources, R.drawable.lightning_rounded, null) as BitmapDrawable).bitmap)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val intent = Intent(context, ToDoActivity::class.java)
        val activity = PendingIntent.getActivity(
                context,
                // this toInt() call (and same below) are a bit of a hack, but should work for a good long while
                notificationId.toInt(),
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )
        builder.setContentIntent(activity)

        val notification = builder.build()

        val notificationIntent = Intent(context, ToDoListNotificationPublisher::class.java)
        notificationIntent.putExtra(ToDoListNotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(ToDoListNotificationPublisher.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(context, notificationId.toInt(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() + delayMillis
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    companion object {
        const val INTENT_ACTION = "com.micseydel.me.snooze"
    }
}
