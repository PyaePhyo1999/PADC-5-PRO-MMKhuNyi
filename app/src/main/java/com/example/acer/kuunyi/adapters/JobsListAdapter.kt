package com.example.acer.kuunyi.adapters

import android.content.Context
import android.view.ViewGroup
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.delegates.JobsListDelegate
import com.example.acer.kuunyi.utils.AppConstant
import com.example.acer.kuunyi.viewholders.JobsItemViewHolder

/**
 * Created by Acer on 7/31/2018.
 */
class JobsListAdapter(context :Context,private val mDelegate : JobsListDelegate) : BaseRecyclerAdapter<JobsItemViewHolder, JobsVO>(context ) {
    private var mLayoutOption: Int = 0
    init {
        mLayoutOption = AppConstant.NEWS_FEED_LAYOUT_ONE
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsItemViewHolder {
        var view = mLayoutInflator.inflate(R.layout.item_jobs_list,parent,false)
        return JobsItemViewHolder(view,mDelegate)
    }

    fun setNewsFeedLayout(layoutOption: Int) {
        mLayoutOption = layoutOption
        notifyDataSetChanged()
    }




}