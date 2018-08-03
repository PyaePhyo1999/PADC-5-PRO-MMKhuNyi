package com.example.acer.kuunyi.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.adapters.JobsListAdapter
import com.example.acer.kuunyi.components.SmartScrollListener
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.events.JobsEvent

import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseActivity() {


        private  lateinit var  mJobsAdapter : JobsListAdapter
        private lateinit var mSmartScrollListener : SmartScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mSmartScrollListener = SmartScrollListener(object :SmartScrollListener.SmartScrollListener{
            override fun onListEndReach() {
                Snackbar.make(rvJobsList,"Loading More Data", Snackbar.LENGTH_LONG).show()


            }

        })

        mJobsAdapter  = JobsListAdapter(applicationContext)
        rvJobsList.layoutManager = LinearLayoutManager(applicationContext)
        rvJobsList.adapter = mJobsAdapter
        rvJobsList.addOnScrollListener(mSmartScrollListener)

        JobModel.getInstance().loadJobsList()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loadedJobsList(event : JobsEvent.JobsListEvent){
        mJobsAdapter.appendNewData(event.getJobsList())

    }
}
