package com.example.acer.kuunyi.mvp.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.delegates.JobsListDelegate
import com.example.acer.kuunyi.mvp.views.JobsListView

/**
 * Created by Acer on 8/3/2018.
 */
class JobsListPresenter : BasePresenter<JobsListView>(),JobsListDelegate {
    override fun onTapJobsList(job : JobsVO) {
       mView.onLaunchDetailJob(job.jobPostId!!)
    }

    private lateinit var jobListLd : MutableLiveData<MutableList<JobsVO>>

    override fun initPresenter(mView: JobsListView) {
        super.initPresenter(mView)
        jobListLd = MutableLiveData()
        JobModel.getInstance().loadJobsList(jobListLd,mErrorLd)
    }
    fun getJobListLd() : LiveData<MutableList<JobsVO>>{
        return jobListLd
    }
}