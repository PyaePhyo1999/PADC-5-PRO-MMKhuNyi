package com.example.acer.kuunyi.viewholders

import android.view.View
import com.example.acer.kuunyi.data.vos.HomeVO
import com.example.acer.kuunyi.data.vos.JobTagsVO
import com.example.acer.kuunyi.data.vos.JobsVO
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_jobs_list.view.*

/**
 * Created by Acer on 7/31/2018.
 */
class JobsItemViewHolder(itemView : View) : BaseViewHolder<JobsVO>(itemView) {
    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setData(data: JobsVO) {
       itemView.tvJobTitle!!.text = data.jobTags!![1].tag
        itemView.tvJobDesc.text=data.fullDesc
    }
}