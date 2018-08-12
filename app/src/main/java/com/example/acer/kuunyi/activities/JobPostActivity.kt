package com.example.acer.kuunyi.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.example.acer.kuunyi.R

import com.example.acer.kuunyi.data.JobModel
import com.example.acer.kuunyi.utils.AppConstant
import com.example.acer.kuunyi.viewpods.AddedPhotoViewPod
import kotlinx.android.synthetic.main.activity_job_post.*

/**
 * Created by Acer on 8/8/2018.
 */
class JobPostActivity : BaseActivity() {


    private lateinit var mProgressDialog: ProgressDialog

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, JobPostActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_post)


        vpAddPhoto.setOnClickListener{
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"select picture"),AppConstant.PICK_IMAGE_REQUEST)
        }

        btnAddPost.setOnClickListener {

            val shortDesc = etShortDesc.text.toString()
//            val price = Integer.parseInt(etJobPrice.text.toString())
//            val duration = etJobDuration.text.toString()

            when {
                TextUtils.isEmpty(shortDesc) -> etShortDesc.error = "Need news content to publish."
//                TextUtils.isEmpty(price.toString()) -> etJobPrice.error = "Need price content to publish."
//                TextUtils.isEmpty(duration) -> etJobDuration.error = "Need days content to publish."
                TextUtils.isEmpty((vpAddedPhoto as AddedPhotoViewPod).getPhotoUrl()) -> Snackbar.make(etShortDesc, "You should select a photo relating to the news.", Snackbar.LENGTH_LONG).show()
                else -> {
                    showProgressDialog("Publishing your Job Post")
                    JobModel.getInstance().upLoadFile( (vpAddedPhoto as AddedPhotoViewPod).getPhotoUrl(),  object : JobModel.upLoadFileCallBack {
                        override fun onUploadSuccess(uploadedPaths: String) {
                            dismissProgressDialog()
                            JobModel.getInstance().addJobPost(shortDesc, uploadedPaths)
                            onBackPressed()
                        }

                        override fun onUploadFailed(msg: String) {
                            Snackbar.make(etShortDesc, "Your photo couldn't be uploaded because : " + msg, Snackbar.LENGTH_LONG).show()
                        }
                    })
                }
            }
        }
    }

    fun showProgressDialog(msg: String) {
        mProgressDialog = ProgressDialog(this, R.style.AppDialog)
        mProgressDialog.setMessage(Html.fromHtml(msg))

        if (!mProgressDialog.isShowing) {
            mProgressDialog.setCancelable(false)
            mProgressDialog.show()
        }

    }

    fun dismissProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==AppConstant.PICK_IMAGE_REQUEST && resultCode== Activity.RESULT_OK && data!=null){
            var uri = data.data
            onPhotoToken(uri.toString())

        }
    }
    private fun onPhotoToken(photoUrl  :String ){
        if (TextUtils.isEmpty(photoUrl)){
            Snackbar.make(etShortDesc, "ERROR : Path to photo is empty.", Snackbar.LENGTH_LONG).show()
        }
        else {
            (vpAddedPhoto as AddedPhotoViewPod).setData(photoUrl)
            vpAddedPhoto.visibility = View.VISIBLE
            vpAddPhoto.visibility=View.GONE
        }
    }



}

