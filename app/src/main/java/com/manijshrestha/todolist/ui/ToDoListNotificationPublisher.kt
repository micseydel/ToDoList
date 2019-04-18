package com.manijshrestha.todolist.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class ToDoListNotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(notificationId, notification)

        val newIntent = Intent()
        newIntent.action = INTENT_ACTION

        // TODO: https://developer.android.com/reference/android/support/v4/content/LocalBroadcastManager
        context.sendBroadcast(newIntent)
    }

    companion object {
        const val NOTIFICATION_ID = "notification_id"
        const val NOTIFICATION = "notification"

        const val YEAR = "year"
        const val MONTH = "month"
        const val DAY = "day"

        const val ADAPTER_POSITION = "adapterPosition"
        const val INSTANT_MILLIS = "instantMillis"

        const val INTENT_ACTION = "me.micseydel.todolist.notification"

        const val DESCRIPTION = "description"

        const val NOTIFICATIONID = "notificationId"
    }
}
