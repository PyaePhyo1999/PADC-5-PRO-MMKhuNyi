package com.example.acer.kuunyi.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by Acer on 8/11/2018.
 */
class AppFirebaseInstanceIdService : FirebaseInstanceIdService(){
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        val token : String = FirebaseInstanceId.getInstance().token!!
        Log.d("TAG","FCM Token : "+token)
    }
}