package com.example.acer.kuunyi.activities

import android.os.Bundle
import okhttp3.internal.Util
import android.content.Intent
import com.example.acer.kuunyi.utils.AppConstant
import com.google.firebase.iid.FirebaseInstanceId


/**
 * Created by Acer on 8/10/2018.
 */
class MainEmptyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityIntent : Intent = if (FirebaseInstanceId.getInstance().token != null) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }
}