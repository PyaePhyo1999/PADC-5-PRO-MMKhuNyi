package com.example.acer.kuunyi.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.data.vos.HomeVO
import com.example.acer.kuunyi.data.vos.JobTagsVO
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.delegates.JobsListDelegate
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_jobs_list.view.*

/**
 * Created by Acer on 7/31/2018.
 */
class JobsItemViewHolder(itemView : View,private val mDelegate: JobsListDelegate) : BaseViewHolder<JobsVO>(itemView) {

    override fun onClick(p0: View?) {
       mDelegate.onTapJobsList(this.mData!!)
    }

    override fun setData(data: JobsVO) {
        mData = data
        itemView.tvJobPostedDate.text=data.postedDate
        itemView.tvJobDesc.text = data.shortDesc
        if (!data.image!!.isEmpty()){
            Glide.with(itemView.context)
                    .load(data.image)
                    .placeholder(R.drawable.placeholder_news_three)
                    .error(R.drawable.placeholder_news_three)
                    .into(itemView.ivJobImage)
        }

//        itemView.tvJobPeriod.text = ""+data.jobDuration!!.totalWorkingDays +"days"
//        itemView.tvJobLocation.text=data.location
//        itemView.tvJobPrice.text=""+ data.offerAmount!!.amount+" Kyats "+ data.offerAmount!!.duration



    }
}