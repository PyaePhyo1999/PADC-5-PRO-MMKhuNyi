package com.example.acer.kuunyi.adapters

import android.content.Context
import android.view.ContextMenu
import android.view.View
import android.view.ViewGroup
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.data.vos.JobTagsVO
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.viewholders.BaseViewHolder
import com.example.acer.kuunyi.viewholders.JobsItemViewHolder
import java.util.*

/**
 * Created by Acer on 7/31/2018.
 */
class JobsListAdapter(context :Context) : BaseAdapter<JobsItemViewHolder, JobsVO>(context ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsItemViewHolder {
        var view = mLayoutInflator.inflate(R.layout.item_jobs_list,parent,false)
        return JobsItemViewHolder(view)
    }


}