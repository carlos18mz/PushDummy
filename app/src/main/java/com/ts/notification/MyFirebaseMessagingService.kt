package com.ts.notification

import android.content.ContentValues.TAG
import android.os.Looper
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Looper.prepare()

        Handler().post {
            println("aaaaaa")
            Toast.makeText(baseContext,"Notification : ${remoteMessage.notification?.title}",Toast.LENGTH_LONG).show()
        }

        /*Handler(Looper.getMainLooper()).postDelayed({
            Toast.makeText(baseContext,"Notification : ${remoteMessage.notification?.title}",Toast.LENGTH_LONG).show()
        }, 3000)*/

        Looper.loop()
        /*
        println("aaaaaaaaaaaaaaaaa")
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            Toast.makeText(baseContext,"Notification : ${remoteMessage.notification?.title}",Toast.LENGTH_LONG).show()

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
        */

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }
}