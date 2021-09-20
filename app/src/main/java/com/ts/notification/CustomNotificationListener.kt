package com.ts.notification

import android.R.id
import android.content.Context
//import com.google.firebase.messaging.RemoteMessage
//import com.microsoft.windowsazure.messaging.notificationhubs.NotificationListener
import android.R.id.message
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
//import com.microsoft.windowsazure.messaging.notificationhubs.NotificationListener

/*
class CustomNotificationListener: NotificationListener {
    override fun onPushNotificationReceived(context: Context?, message: RemoteMessage?) {

        /* The following notification properties are available. */
        /* The following notification properties are available. */
        val notification: RemoteMessage.Notification = message?.notification!!
        val title: String = notification?.getTitle()!!
        val body: String = notification?.getBody()!!
        val data: Map<String, String> = message.getData()

        if (id.message != null) {
            Log.d(TAG, "Message Notification Title: $title")
            Log.d(TAG, "Message Notification Body: " + id.message)
        }

        if (data != null) {
            for ((key, value) in data) {
                Log.d(TAG, "key, $key value $value")
            }
        }


    }
}

 */