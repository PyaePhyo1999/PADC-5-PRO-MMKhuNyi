package com.example.acer.kuunyi.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.utils.AppConstant
import com.google.android.gms.auth.api.Auth

import kotlinx.android.synthetic.main.activity_login.*


/**
 * Created by Acer on 8/1/2018.
 */
open class LoginActivity : BaseActivity(){
    companion object {
        fun newIntent(context :Context) : Intent{
            return Intent(context,LoginActivity::class.java)
        }
    }

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

