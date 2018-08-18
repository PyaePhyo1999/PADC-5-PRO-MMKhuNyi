package com.example.acer.kuunyi.data

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.Uri
import com.example.acer.kuunyi.adapters.JobsListAdapter
import com.example.acer.kuunyi.data.vos.JobTagsVO
import com.example.acer.kuunyi.data.vos.JobsVO
import com.example.acer.kuunyi.events.JobsEvent
import com.example.acer.kuunyi.utils.AppConstant
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import org.greenrobot.eventbus.EventBus
import java.lang.Exception
import java.util.ArrayList

import kotlin.collections.HashMap

/**
 * Created by Acer on 8/2/2018.
 */
class JobModel private constructor(context: Context) {

    private var mDatabaseReference: DatabaseReference? = null
    private lateinit var mJobsFeedDR: DatabaseReference
    var jobsList = ArrayList<JobsVO>()

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

    fun getJobsId (jobsId : Int): JobsVO? {
        jobsList
                .filter { it.jobPostId!! == jobsId }
                .forEach { return it }
         return null

    }

    fun loadJobsList(mJobsListLd: MutableLiveData<MutableList<JobsVO>>, errorLd: MutableLiveData<String>) {
        mJobsFeedDR = mDatabaseReference!!.child(AppConstant.JOBS_POST)
        mJobsFeedDR.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataSnapshot.children?.forEach { snapShot: DataSnapshot ->
                        val jobs: JobsVO = snapShot.getValue(JobsVO::class.java)!!
                        jobsList.add(jobs)

                }

                mJobsListLd.value = jobsList
            }

        })


    }
    fun isUserLogin(): Boolean{
        return mFirebaseUser!=null
    }
    fun authenicateUserWithGoogleAccount(signInAccount : GoogleSignInAccount,delegate : SignInWithGoogleAccountDelegate) {
        var credentail: AuthCredential = GoogleAuthProvider.getCredential(signInAccount.idToken, null)
        mFirebaseAuth!!.signInWithCredential(credentail)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful){
                        delegate.onFailureSignIn(task.exception!!.message!!)
                    } else {
                        delegate.onSuccessSignIn(signInAccount)
                    }
                }.addOnFailureListener { e -> delegate.onFailureSignIn(e.message!!) }

    }
     interface SignInWithGoogleAccountDelegate {
         fun onSuccessSignIn(signInAccount: GoogleSignInAccount)

         fun onFailureSignIn(msg: String)

    }

    interface upLoadFileCallBack {
        fun onUploadSuccess(uploadPaths: String)
        fun onUploadFailed(msg: String)

    }
    fun addJobPost(shortDesc : String,image : String)
    {
       var  jobsVO = JobsVO.jobFeed(shortDesc,image)
        mJobsFeedDR.child(jobsVO.postedDate).setValue(jobsVO)
    }

    fun upLoadFile (fileToUpload : String , upLoadFileCallBack : upLoadFileCallBack){
        val file  : Uri =Uri.parse(fileToUpload)
        val storage  :FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef:StorageReference = storage.getReferenceFromUrl(AppConstant.FIREBASE_STORAGE_BUCKET)
        val upLoadFileRef : StorageReference = storageRef.child(AppConstant.UPLOAD_IMAGE_PATH + "/" + file.lastPathSegment)
        val upLoadTask : UploadTask = upLoadFileRef.putFile(file)
        upLoadTask.addOnFailureListener {
            e -> upLoadFileCallBack.onUploadFailed(e.localizedMessage)
        }.addOnSuccessListener { taskSnapshot ->
            val upLoadImageUrl: Uri = taskSnapshot!!.downloadUrl!!
            upLoadFileCallBack.onUploadSuccess(upLoadImageUrl.toString())
        }

    }

}

