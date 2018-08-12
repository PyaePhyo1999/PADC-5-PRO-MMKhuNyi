package com.example.acer.kuunyi.viewpods

import android.content.Context
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.acer.kuunyi.R
import kotlinx.android.synthetic.main.view_pod_added_photo.view.*

/**
 * Created by Acer on 8/9/2018.
 */
class AddedPhotoViewPod : RelativeLayout {
    private lateinit var  mPhotoUrl : String

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    fun setData(photoUrl : String){
        mPhotoUrl = photoUrl
        Glide.with(context)
                .load(photoUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder_news_three)
                .error(R.drawable.placeholder_news_three)
                .into(ivAddedPhoto)

    }
    fun getPhotoUrl() : String{
        return mPhotoUrl
    }


    }
