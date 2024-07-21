package com.saurav.boozebuddy.notification_service

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.util.Random

class ScheduleNotification(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: "Reminder"
        val message = inputData.getString("message") ?: "You have a reminder"
        Log.d("This is notification title and message", "$title and $message")
        val notificationHelper = ScheduleNotificationService(applicationContext)
        notificationHelper.showNotification(title, message, Random().nextInt())
        return Result.success()
    }
}
