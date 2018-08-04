package com.example.acer.kuunyi.activities

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import butterknife.OnClick
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.mvp.presenters.LoginPresenter
import com.example.acer.kuunyi.mvp.views.LoginView
import com.example.acer.kuunyi.utils.AppConstant
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient

import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Acer on 8/1/2018.
 */
class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener{
    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private var mGoogleApiClient : GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(AppConstant.REQUEST_ID_TOKEN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(applicationContext)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build()

        btnLogin.setOnClickListener {
            intent = MainActivity.newIntent(applicationContext)
            startActivity(intent)
        }

        btnLoginWithGoogle.setOnClickListener{
            if (JobModel.getInstance().isUserLogin()){
                startNewActivity()
            }
            else {

                onTapLoginWithGoogle()
            }

        }

    }

   private fun onTapLoginWithGoogle (){
       var signInIntent : Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
       startActivityForResult(signInIntent,AppConstant.RC_GOOGLE_SIGN_IN)

   }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== AppConstant.RC_GOOGLE_SIGN_IN)
        {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            processGoogleSignInResult(result)
        }
    }
    private fun startNewActivity(){
        startActivity(MainActivity.newIntent(applicationContext))
    }

    private fun processGoogleSignInResult(signInResult  :GoogleSignInResult){
        if (signInResult.isSuccess) {
            // Google Sign-In was successful, authenticate with Firebase
            val account = signInResult.signInAccount
            if (account != null) {
                JobModel.getInstance().authenicateUserWithGoogleAccount(account, object : JobModel.SignInWithGoogleAccountDelegate {
                    override fun onSuccessSignIn(signInAccount: GoogleSignInAccount) {
                        startNewActivity()
                        Toast.makeText(applicationContext,"Success Login", Toast.LENGTH_SHORT).show()
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