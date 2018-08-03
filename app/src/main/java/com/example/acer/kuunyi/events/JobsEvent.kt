package com.example.acer.kuunyi.events

import com.example.acer.kuunyi.data.vos.HomeVO
import com.example.acer.kuunyi.data.vos.JobTagsVO
import com.example.acer.kuunyi.data.vos.JobsVO

/**
 * Created by Acer on 8/2/2018.
 */
class JobsEvent {
       companion object
           class JobsListEvent(){
               private var jobsList: MutableList<JobsVO>?=null

               fun JobListLoadedEvent(jobsListData : MutableList<JobsVO>) {
                   this.jobsList = jobsListData
               }

               fun getJobsList(): MutableList<JobsVO> {
                   return jobsList!!
               }
           }




}