package com.paytv.premiere.smartwatch

import android.app.Notification
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

const val CHANNEL_NAME = "Sample Channel Name"
const val CHANNEL_DESCRIPTION = "Sample Channel Description"
const val CHANNEL_ID = "Channel Id (but as String)"

const val INTERACTIVE_NOTIFICATION_ID = 13
fun createInteractiveNotification(
    context: Context,
    homeTeamName: String,
    awayTeamName: String
): Notification {
    val remoteViews = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_content)
    val remoteViewsc = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_colapsed)

    val uri = Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.resources.getResourcePackageName(R.drawable.resized_image_promo) +
                '/' + context.resources.getResourceTypeName(R.drawable.resized_image_promo) +
                '/' + context.resources.getResourceEntryName(R.drawable.resized_image_promo)
    )

//    val message = NotificationCompat.MessagingStyle.Message(
//        "sticker",
//        1.toLong(),
//        null
//    )
//        .setData("image/png", uri)

    return NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setContentIntent(InteractiveNotificationBroadcastReceiver.newPendingIntent(context))
        .setContentTitle("GOLLLL!!")
        .setContentText("${homeTeamName} 2 X 3 ${awayTeamName}")
        .setColorized(true)
        .setColor(context.resources.getColor(R.color.green))
        .setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(generateBitmapFromVectorDrawable(context, R.drawable.logo_vas))
                .setBigContentTitle("GOLLLL!!")
                .bigLargeIcon(generateBitmapFromVectorDrawable(context, R.drawable.logo_vas))
        )
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