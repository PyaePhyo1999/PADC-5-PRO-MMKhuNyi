package com.example.acer.kuunyi.utils

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.TaskStackBuilder
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.example.acer.kuunyi.R
import com.example.acer.kuunyi.activities.MainActivity
import org.mmtextview.MMFontUtils
import java.util.concurrent.ExecutionException

/**
 * Created by Acer on 8/11/2018.
 */
class NotificationUtils {
    companion object {


        private fun encodeResourceToBitmap(context: Context, resourceId : Int): Bitmap? {
            var bitmap : Bitmap? = null
            val largeIconWidth = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
            val largeIconHeight = context.resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)
            try {
                bitmap = Glide.with(context)
                        .load(resourceId)
                        .asBitmap()
                        .into(largeIconWidth, largeIconHeight)
                        .get()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }


            return bitmap

        }

        fun notifyCustomMsg(context: Context,message: String ){
            val title : String =context.getString(R.string.launcher_name)
            val mmMessage : String =  message
            val appIcon : Bitmap = this.encodeResourceToBitmap(context,R.mipmap.ic_news_from_people_noti)!!
            val bigTextStyle  : NotificationCompat.BigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.bigText(mmMessage)

            val builder = NotificationCompat.Builder(context)
                    .setColor(context.resources.getColor(R.color.accent))
                    .setSmallIcon(R.drawable.ic_news_from_people_noti)
                    .setLargeIcon(appIcon)
                    .setContentTitle(title)
                    .setContentText(mmMessage)
                    .setAutoCancel(true)
                    .setStyle(bigTextStyle)
            saveNewsAction(context,AppConstant.NOTIFICATION_ID_NEW_MESSAGE,builder)



            val resultIntent = MainActivity.newIntentNotiBody(context)

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            builder.setContentIntent(resultPendingIntent)

            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(AppConstant.NOTIFICATION_ID_NEW_MESSAGE, builder.build())

        }
        private fun saveNewsAction(context: Context, notificationId: Int, builder: NotificationCompat.Builder) {
            //Intent to execute when user tap on Action Button.
            val saveNewsActionIntent = MainActivity.newIntentSaveJob(context, notificationId)
            val playSongsByArtistActionPendingIntent = PendingIntent.getActivity(context,AppConstant.REQUEST_ID_SAVE_JOB, saveNewsActionIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            //"Save News" Action Label.
            val notiActionSaveNews = context.getString(R.string.noti_action_save_news)

            //Action Button Icon.
            val actionIcon = R.drawable.ic_bookmark_border_24dp
            if (TextUtils.equals(Build.BRAND, AppConstant.VENDOR_XIAOMI)) {
                //actionIcon = R.drawable.ic_other_bookmark_border_24dp;
            }

            val playSongsByArtistAction = NotificationCompat.Action(actionIcon,
                    notiActionSaveNews, playSongsByArtistActionPendingIntent)
            builder.addAction(playSongsByArtistAction)
        }



    }




}