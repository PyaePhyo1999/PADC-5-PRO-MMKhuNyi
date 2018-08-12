package com.example.acer.kuunyi.fcm

import android.util.Log
import com.example.acer.kuunyi.utils.AppConstant
import com.example.acer.kuunyi.utils.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Acer on 8/11/2018.
 */
class AppFirebaseMessagingService : FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage){
        Log.d("TAG", "FCM Message : " + remoteMessage.messageId)
        Log.d("TAG", "FCM Notification Message: " + remoteMessage.notification)
        Log.d("TAG", "FCM Data Message: " + remoteMessage.data)

        val remoteMsgData = remoteMessage.data
        val message = remoteMsgData[AppConstant.KEY_MESSAGE]

        if (message != null) {
            NotificationUtils.notifyCustomMsg(applicationContext, message)
        }


    }

}