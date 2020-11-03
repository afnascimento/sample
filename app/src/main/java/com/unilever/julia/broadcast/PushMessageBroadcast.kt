package com.unilever.julia.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.logger.Logger

class PushMessageBroadcast(private val mListener : Listener) : BroadcastReceiver() {

    interface Listener {
        fun onReceivePushNotification(notification : FirebaseNotification)
    }

    companion object {
        const val TAG = "PushMessageBroadcast"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.debug(TAG, intent?.action.toString())
        if (intent?.action == BroadcastConstants.ACTION_PUSH) {
            val notification = intent.getParcelableExtra<FirebaseNotification>(BroadcastConstants.EXTRA_NOTIFICATION)
            mListener.onReceivePushNotification(notification)
        }
    }
}