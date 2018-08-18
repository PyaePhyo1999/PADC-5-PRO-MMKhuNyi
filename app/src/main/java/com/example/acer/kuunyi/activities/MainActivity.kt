package com.example.acer.kuunyi.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
import android.support.v4.app.INotificationSideChannel
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.view.GravityCompat
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
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.events.JobsEvent
import com.example.acer.kuunyi.mvp.presenters.JobsListPresenter
import com.example.acer.kuunyi.mvp.views.JobsListView
import com.example.acer.kuunyi.utils.AppConstant
import com.example.acer.kuunyi.utils.AppConstant.Companion.LAUNCH_ACTION_TAP_NOTI_BODY
import com.example.acer.kuunyi.utils.AppConstant.Companion.LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION
import com.google.android.gms.appinvite.AppInviteInvitation
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


class MainActivity : BaseActivity(), JobsListView {
    private lateinit var mFirebaseRemoteConfig  :FirebaseRemoteConfig
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }

        fun newIntentSaveJob(context: Context, notificationId: Int): Intent {
            return Intent(context, MainActivity::class.java)
                    .putExtra(AppConstant.IE_NOTIFICATION_ID, notificationId).putExtra(AppConstant.IE_LAUNCH_ACTION, AppConstant.LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION)
        }

        fun newIntentNotiBody(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
                    .putExtra(AppConstant.IE_LAUNCH_ACTION, AppConstant.LAUNCH_ACTION_TAP_NOTI_BODY)

        }

    }

    override fun onLaunchDetailJob(JobsId: Int) {
        val intent = JobDetailActivity.newIntent(applicationContext, JobsId)
        startActivity(intent)
    }

    private lateinit var mPresenter: JobsListPresenter
    private lateinit var mJobsAdapter: JobsListAdapter
    private lateinit var mSmartScrollListener: SmartScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initRemoteConfig()
        fetchRemoteConfig()


        fab.setOnClickListener {
            var intent = JobPostActivity.newIntent(applicationContext)
            startActivity(intent)
        }
        ivInviteToApp.setOnClickListener {
            sendInvitation()
        }

        if (supportActionBar != null) {
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        mPresenter = ViewModelProviders.of(this).get(JobsListPresenter::class.java)
        mPresenter.initPresenter(this)
        mSmartScrollListener = SmartScrollListener(object : SmartScrollListener.SmartScrollListener {
            override fun onListEndReach() {
                Snackbar.make(rvJobsList, "Loading More Data", Snackbar.LENGTH_LONG).show()


            }

        })
        mPresenter.getJobListLd().observe(this, Observer<MutableList<JobsVO>> { t -> displayJobsListData(t!!) })

        mJobsAdapter = JobsListAdapter(applicationContext, mPresenter)
        rvJobsList.layoutManager = LinearLayoutManager(applicationContext)
        rvJobsList.adapter = mJobsAdapter

        rvJobsList.addOnScrollListener(mSmartScrollListener)
        val bundle = intent.extras
        if (bundle != null) {
            val notificationId = bundle.getInt(AppConstant.IE_NOTIFICATION_ID)
            if (notificationId != 0) {
                dismissNotification(notificationId)
            }

            val launchAction = bundle.getInt(AppConstant.IE_LAUNCH_ACTION)
            when (launchAction) {
                AppConstant.LAUNCH_ACTION_TAP_SAVE_NEWS_NOTI_ACTION -> Snackbar.make(rvJobsList, "\"Save News\" from notification action!", Snackbar.LENGTH_SHORT).show()
                AppConstant.LAUNCH_ACTION_TAP_NOTI_BODY -> Snackbar.make(rvJobsList, "Tapped notification body", Snackbar.LENGTH_SHORT).show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppConstant.RC_GOOGLE_SIGN_IN) {
            val result: GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            processGoogleSignInResult(result)
        } else if (requestCode == AppConstant.RC_INVITE_TO_APP) {
            if (resultCode == Activity.RESULT_OK) {
                val ids = AppInviteInvitation.getInvitationIds(requestCode, data!!)
                Snackbar.make(rvJobsList, "Invitations sent to " + ids.size + " friends", Snackbar.LENGTH_SHORT).show()

            } else {
                Snackbar.make(rvJobsList, "Failed to send invitation.", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

    private fun sendInvitation() {
        val intent = AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build()
        startActivityForResult(intent, AppConstant.RC_INVITE_TO_APP)
    }

    private fun displayJobsListData(jobsList: MutableList<JobsVO>) {
        mJobsAdapter.clearData()
        mJobsAdapter.setNewData(jobsList)
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
        var id = item.itemId
        if (id == R.id.action_settings) {
            return true
        } else if (id == android.R.id.home) {
            drawLayout.openDrawer(GravityCompat.START)
        }

        return super.onOptionsItemSelected(item)


    }

    private fun dismissNotification(notificationID: Int) {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.cancel(notificationID)
    }

    private fun initRemoteConfig() {
        // Initialize Firebase Remote Config.
         mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

        // Define Firebase Remote Config Settings.
        val firebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build()

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        val defaultConfigMap = HashMap<String, Any>()
        defaultConfigMap.put(AppConstant.RC_NEWS_FEED_LAYOUT, 1)

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings)
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap)
    }

    private fun fetchRemoteConfig() {
        var cacheExpiration: Long = 3600 // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mFirebaseRemoteConfig.info.configSettings
                .isDeveloperModeEnabled) {
            cacheExpiration = 0
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener({
                    // Make the fetched config available via
                    // FirebaseRemoteConfig get<type> calls.
                    mFirebaseRemoteConfig.activateFetched()
                    applyRetrievedLengthLimit()
                })
                .addOnFailureListener({ e ->
                    // There has been an error fetching the config
                    Log.d("TAG", "Error fetching config: " + e.message)
                    applyRetrievedLengthLimit()
                })
    }

    private fun applyRetrievedLengthLimit() {
        val newsFeedLayout = mFirebaseRemoteConfig.getLong(AppConstant.RC_NEWS_FEED_LAYOUT)
        mJobsAdapter.setNewsFeedLayout(newsFeedLayout!!.toInt())
        Log.d("TAG", "newsFeedLayout : " + newsFeedLayout!!)
    }
}
