package com.paytv.premiere.smartwatch

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class InteractiveNotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("InteractiveNotificationBroadcastReceiver", "onReceive")
    }

    companion object {
        fun newPendingIntent(context: Context): PendingIntent {
            val intent = Intent(context, InteractiveNotificationBroadcastReceiver::class.java)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}