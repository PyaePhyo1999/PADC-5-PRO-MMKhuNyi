package com.example.acer.kuunyi.data

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.acer.kuunyi.adapters.JobsListAdapter
import com.example.acer.kuunyi.data.vos.JobTagsVO
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.events.JobsEvent
import com.example.acer.kuunyi.utils.AppConstant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList

/**
 * Created by Acer on 8/2/2018.
 */
class JobModel private constructor(context: Context) {

    private var mDatabaseReference: DatabaseReference? = null
    private lateinit var mJobsFeedDR: DatabaseReference

    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseUser: FirebaseUser? = null

    companion object {
        private var objInstance: JobModel? = null
        fun getInstance(): JobModel {
            if (objInstance == null) {
                throw RuntimeException("JobsModel is  being invoked before initializing")
            }
            val i = objInstance
            return i!!

        }

        fun initJobsAppModel(context: Context) {
            objInstance = JobModel(context)
        }
    }

    init {
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth!!.currentUser
    }

    fun loadJobsList(mJobsListLd: MutableLiveData<List<JobsVO>>, errorLd: MutableLiveData<String>) {
        mJobsFeedDR = mDatabaseReference!!.child(AppConstant.JOBS_POST)
        mJobsFeedDR.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                    var jobsList = ArrayList<JobsVO>()
                    dataSnapshot?.children?.forEach { snapShot: DataSnapshot ->
                        var jobs: JobsVO = snapShot.getValue(JobsVO::class.java)!!
                        jobsList.add(jobs)

                    }

                    mJobsListLd.value = jobsList
                }
            }

        })


    }
}
