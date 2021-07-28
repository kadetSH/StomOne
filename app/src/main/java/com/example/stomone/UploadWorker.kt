package com.example.stomone

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    companion object {
        const val CHANNEL_ID = "channel"
    }

    val context: Context = this.applicationContext

    override suspend fun doWork(): Result {
        val titlePush: String? = inputData.getString(context.getString(R.string.uploadWorker_title))
        val messagePush: String? =
            inputData.getString(context.getString(R.string.uploadWorker_message))
        if ((titlePush != null) && (messagePush != null)) {
            message(PushItem(titlePush, messagePush), titlePush, messagePush)
        }
        return Result.success()
    }

    private fun message(
        pushItem: PushItem, titlePush: String, messagePush: String
    ) {
        val alarmManager: AlarmManager? = null
        val intent = Intent(applicationContext, MainActivity::class.java)
//        intent.apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            putExtra("title", titlePush)
//            putExtra("message", messagePush)
//        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_CANCEL_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.uploadWorker_channelName)
            val description =
                context.getString(R.string.uploadWorker_channelDescription)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                ContextCompat.getSystemService(context, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.medical_services_20)
            .setContentTitle(pushItem.titlePush)
            .setContentText(pushItem.messagePush)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
//            .addAction(
//                R.drawable.baseline_person_18,
//                context.getString(R.string.uploadWorker_clickMe),
//                pendingIntent
//            )
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(101, builder.build())

        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 1000L,
            pendingIntent
        )
    }

}