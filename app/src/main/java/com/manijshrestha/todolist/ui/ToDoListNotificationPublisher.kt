package com.manijshrestha.todolist.ui

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class ToDoListNotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        System.out.println("onReceive")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(notificationId, notification)

        Intent().also { newIntent -> // FIXME: also()? wtf?
            newIntent.action = "com.an.sms.example"
//            intent.putExtra("data", "Notice me senpai!")
            context.sendBroadcast(newIntent)
        }
    }

    companion object {
        var NOTIFICATION_ID = "notification_id"
        var NOTIFICATION = "notification"
    }
}
