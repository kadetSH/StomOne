package com.example.stomone

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.concurrent.TimeUnit

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title.toString()
        val message = remoteMessage.notification?.body.toString()
        workManagerReminder(title, message)
    }

    private fun workManagerReminder(
        title: String,
        message: String
    ) {
        val myData: Data = Data.Builder()
            .putString(applicationContext.resources.getString(R.string.uploadWorker_title), title)
            .putString(applicationContext.resources.getString(R.string.uploadWorker_message), message)
            .build()

        val myWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setInitialDelay(1000, TimeUnit.MILLISECONDS)
            .setInputData(myData)
            .build()
        WorkManager.getInstance().enqueue(myWorkRequest)
    }

}