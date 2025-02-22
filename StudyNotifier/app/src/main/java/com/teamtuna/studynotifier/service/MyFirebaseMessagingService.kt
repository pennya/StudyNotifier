package com.teamtuna.studynotifier.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.teamtuna.studynotifier.ui.MainActivity
import com.teamtuna.studynotifier.R
import com.teamtuna.studynotifier.util.PP


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
        sendRegistrationToLocal(token)
        println(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        sendNotification(
            remoteMessage?.notification?.title,
            remoteMessage?.notification?.body)
    }

    private fun sendRegistrationToServer(token: String?) {
        // TODO  push api server

        // 회원인 경우 token 값 일치하는지 비교
        // 토근값 변경 || 토큰값 없는 경우 업데이트
    }

    private fun sendRegistrationToLocal(token: String?) {
        token?.let {
            PP.PUSH_TOKEN.set(it)
        }
    }

    private fun sendNotification(
        messageTitle: String?,
        messageBody: String?
    ) {
        if (messageTitle.isNullOrEmpty() || messageBody.isNullOrEmpty())
            return

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.notification_channel)
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = getString(R.string.notification_channel)
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}
