package com.paytv.premiere.smartwatch

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

const val CHANNEL_NAME = "Sample Channel Name"
const val CHANNEL_DESCRIPTION = "Sample Channel Description"
const val CHANNEL_ID = "Channel Id (but as String)"

const val INTERACTIVE_NOTIFICATION_ID = 13
fun createInteractiveNotification(context: Context): Notification {
    val remoteViews = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_content)

    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_team_placeholder)
        .setColor(ContextCompat.getColor(context, R.color.green))
        .setLargeIcon(
            generateBitmapFromVectorDrawable(
                context,
                R.drawable.ic_team_placeholder
            )
        )
        .setColorized(true)
        .setContentIntent(InteractiveNotificationBroadcastReceiver.newPendingIntent(context))
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setCustomContentView(remoteViews)
        .build()
}

fun notifyNotification(context: Context, notificationId: Int, notification: Notification) =
    NotificationManagerCompat.from(context).notify(notificationId, notification)

fun generateBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId) as Drawable
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}