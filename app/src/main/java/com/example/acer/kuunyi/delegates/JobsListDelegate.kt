package com.example.acer.kuunyi.delegates

import com.example.acer.kuunyi.data.vos.JobsVO

/**
 * Created by Acer on 8/3/2018.
 */
interface JobsListDelegate {
    fun onTapJobsList(job : JobsVO)
}