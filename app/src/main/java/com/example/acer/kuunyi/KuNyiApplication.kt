package com.example.acer.kuunyi

import android.app.Application
import com.example.acer.kuunyi.data.JobModel

/**
 * Created by Acer on 8/2/2018.
 */
class KuNyiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        JobModel.initJobsAppModel(applicationContext)
    }

}