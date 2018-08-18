package com.example.acer.kuunyi.activities

import android.os.Bundle
import okhttp3.internal.Util
import android.content.Intent
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.utils.AppConstant
import com.google.firebase.iid.FirebaseInstanceId


/**
 * Created by Acer on 8/10/2018.
 */
class MainEmptyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (JobModel.getInstance().isUserLogin())
        {
            startNewActivity()
        }
        else{
            val activityIntent = LoginActivity.newIntent(applicationContext)
            startActivity(activityIntent)
        }


        finish()
    }

}