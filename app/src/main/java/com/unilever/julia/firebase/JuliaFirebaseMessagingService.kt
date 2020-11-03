package com.unilever.julia.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.unilever.julia.R
import android.app.NotificationChannel
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.unilever.julia.broadcast.BroadcastConstants
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.firebase.parser.FirebaseParser
import com.unilever.julia.logger.Logger

class JuliaFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Logger.debug(TAG, "Refreshed token: $token")
    }

    companion object {
        private const val TAG = "FirebaseCloudMessaging"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Logger.debug(TAG, "Message Notification From: " + remoteMessage.from)

        // Check if message contains a data payload.
        val data = remoteMessage.data
        if (data.isNotEmpty()) {
            try {
                val notification = FirebaseParser().parser(data)
                if (notification != null) {
                    createNotification(notification)
                }
            } catch (e : Throwable) {
                e.printStackTrace()
            }
        }
    }

    private fun createNotification(data : FirebaseNotification) {
        val context : Context = this@JuliaFirebaseMessagingService

        val intentBroadcast = Intent(BroadcastConstants.ACTION_PUSH)
        intentBroadcast.putExtra(BroadcastConstants.EXTRA_NOTIFICATION, data)
        context.sendBroadcast(intentBroadcast)

        val intent = data.getIntent(context)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var colorRes : Int = ContextCompat.getColor(context, R.color.fcm_push_color)
        if (data.getColor().isNotEmpty()) {
            colorRes = try {
                Color.parseColor(data.getColor())
            } catch (e : Throwable) {
                ContextCompat.getColor(context, R.color.fcm_push_color)
            }
        }

        var smallIconRes : Int = R.drawable.fcm_push_icon
        if (data.getIcon().isNotEmpty()) {
            smallIconRes = try {
                context.resources.getIdentifier(data.getIcon(), "drawable", context.packageName)
            } catch (e : Throwable) {
                R.drawable.fcm_push_icon
            }
        }

        val notificationID = 1
        //val messageCount = 8
        val channelId = ""
        val notification = NotificationCompat.Builder(this, channelId)
            .setColor(colorRes)
            .setSmallIcon(smallIconRes)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setStyle(NotificationCompat
                .BigTextStyle()
                .setBigContentTitle(data.getTitle())
                .bigText(data.getBody())
            )
            .setContentTitle(data.getTitle())
            .setContentText(data.getBody())
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(pendingIntent)
            //.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            //.setNumber(messageCount)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo data channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationID, notification)

        /*
        val builder = NotificationCompat.Builder(context, "")

        // Add your branding color on 5.0+ devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setSmallIcon(R.drawable.fcm_push_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setStyle(NotificationCompat
                    .BigTextStyle()
                    .setBigContentTitle(title)
                    .bigText(body)
                )
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
        }

        builder.setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.URI_COLUMN_INDEX))
            .setContentIntent(pendingIntent)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
        */
    }
}