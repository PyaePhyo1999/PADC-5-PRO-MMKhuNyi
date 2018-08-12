package com.example.acer.kuunyi.activities

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.android.gms.appinvite.AppInvite


/**
 * Created by Acer on 8/1/2018.
 */
open class LoginActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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




    }

