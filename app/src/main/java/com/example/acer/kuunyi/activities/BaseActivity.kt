package com.example.acer.kuunyi.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.R.id.clLogin
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.utils.AppConstant
import com.google.android.gms.appinvite.AppInvite
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Acer on 7/31/2018.
 */
open class BaseActivity : AppCompatActivity() , GoogleApiClient.OnConnectionFailedListener{
    var mGoogleApiClient : GoogleApiClient? = null

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getGoogleClientApi()


    }
    protected fun getGoogleClientApi(){
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(AppConstant.REQUEST_ID_TOKEN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .addApi(AppInvite.API)
                .build()
    }
    protected fun startNewActivity(){
        startActivity(MainActivity.newIntent(applicationContext))
    }

    fun processGoogleSignInResult(signInResult: GoogleSignInResult) {
        if (signInResult.isSuccess) {
            // Google Sign-In was successful, authenticate with Firebase
            val account = signInResult.signInAccount
//            val idToken = account!!.idToken
//            val sharedPreferences = getSharedPreferences(AppConstant.SHARE_PREFERENCE, Context.MODE_PRIVATE)
//            sharedPreferences.getString(idToken,AppConstant.ID_TOKEN)

            if (account != null) {
                JobModel.getInstance().authenicateUserWithGoogleAccount(account, object : JobModel.SignInWithGoogleAccountDelegate {
                    override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                        startNewActivity()
                        Toast.makeText(applicationContext, "Success Login", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailureSignIn(msg: String) {
                        Log.e("ABC", "Google Sign-In failed.")
                        Snackbar.make(clLogin, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show()

                    }
                })
            }
        } else {
            // Google Sign-In failed
            Log.e("ABC", "Google Sign-In failed.")
            Snackbar.make(clLogin, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show()


        }

    }
}


