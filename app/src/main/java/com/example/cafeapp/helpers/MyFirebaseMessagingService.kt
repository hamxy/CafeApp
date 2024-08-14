package com.example.cafeapp.helpers

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**TODO
 * https://firebase.google.com/docs/reference/android/com/google/firebase/messaging/FirebaseMessagingService
 * */

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
    }

    override fun onNewToken(token: String) {
        // Send new registration token to your server (if applicable)
    }
}
