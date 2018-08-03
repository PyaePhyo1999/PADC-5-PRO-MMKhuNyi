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
               private var jobsList: List<JobsVO>?=null

               fun JobsListLoadedEvent(jobsListData : List<JobsVO>) {
                   this.jobsList = jobsListData
               }

               fun getJobsList(): List<JobsVO> {
                   return jobsList!!
               }
           }




}