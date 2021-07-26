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
            message(PushItem(titlePush, messagePush))
        }
        return Result.success()
    }

    private fun message(
        pushItem: PushItem
    ) {
        val alarmManager: AlarmManager? = null
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(
            context.getString(R.string.uploadWorker_item),
            pushItem
        )
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 1000L,
            pendingIntent
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.uploadWorker_channelName)
            val description =
                context.getString(R.string.uploadWorker_channelDescription)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager =
                ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.medical_services_20)
            .setContentTitle(pushItem.titlePush)
            .setContentText(pushItem.messagePush)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .addAction(
                R.drawable.baseline_person_18,
                context.getString(R.string.uploadWorker_clickMe),
                pendingIntent
            )
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(2, builder.build())
    }


}