package com.example.acer.kuunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.mvp.views.JobsDetailView

/**
 * Created by Acer on 8/4/2018.
 */
class JobsDetailPresenter : BasePresenter<JobsDetailView>() {
    private lateinit var jobsIdLd : MutableLiveData<JobsVO>
    override fun initPresenter(mView: JobsDetailView) {
        super.initPresenter(mView)
        jobsIdLd = MutableLiveData()

    }
    fun getJobId(jobsId : Int): LiveData<JobsVO> {
       var job: JobsVO? = JobModel.getInstance().getJobsId(jobsId)
        jobsIdLd.value=job
        return jobsIdLd
    }
}