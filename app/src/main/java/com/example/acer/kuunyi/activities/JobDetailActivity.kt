package com.example.acer.kuunyi.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.mvp.presenters.JobsDetailPresenter
import com.example.acer.kuunyi.mvp.views.JobsDetailView
import com.example.acer.kuunyi.utils.AppConstant
import kotlinx.android.synthetic.main.activity_job_detail.*
import kotlinx.android.synthetic.main.item_jobs_list.*

/**
 * Created by Acer on 8/2/2018.
 */
class JobDetailActivity : BaseActivity(),JobsDetailView {

    private fun displayJobsDetail(Job: JobsVO) {
        tvJobDetailDesc.text= Job.fullDesc
    }

    private lateinit var mPresenter : JobsDetailPresenter

        companion object {
            fun newIntent(context : Context,JobsId : Int) : Intent{
                return Intent(context,JobDetailActivity::class.java).putExtra(AppConstant.IE_JOBS_ID,JobsId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        val w = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        mPresenter = ViewModelProviders.of(this).get(JobsDetailPresenter::class.java)
        mPresenter.initPresenter(this)

        val jobsId : Int = intent.getIntExtra(AppConstant.IE_JOBS_ID,-1)
        mPresenter.getJobId(jobsId).observe(this, Observer<JobsVO> { t ->

                displayJobsDetail(t!!)

        })
        btnBackDetail.setOnClickListener{
            onBackPressed()
        }




    }


}