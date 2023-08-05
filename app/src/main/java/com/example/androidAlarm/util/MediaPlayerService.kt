package com.example.androidAlarm.util

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.androidAlarm.MainActivity
import com.example.androidAlarm.model.ServiceState
import timber.log.Timber

class MediaPlayerService(
    private val mediaPlayer: MediaPlayer = MediaPlayer()
) : Service() {
    override fun onCreate() {
        super.onCreate()
        Timber.d("create")
    }

    override fun onBind(p0: Intent?): IBinder? {
        Timber.d("bind")
        return TODO("Provide the return value")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("destroy")
        mediaPlayer.stop()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "1",
            "title",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        val notification = Notification.Builder(applicationContext, "1")
            .setContentTitle("title") // android標準アイコンから
            .setSmallIcon(R.drawable.ic_media_play)
            .setContentText("MediaPlay")
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .build()

        // startForeground
        startForeground(1, notification)

        Timber.d("command")

        if (intent!!.getStringExtra("serviceState").equals(ServiceState.SNOOZE.name)) {
            mediaPlayer.stop()
            return START_NOT_STICKY
        }
        mediaPlayer.setDataSource(
            applicationContext,
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        )
        mediaPlayer.prepare()
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "myapp://alarmTimeDetail".toUri(),
                this,
                MainActivity::class.java // <-- Notice this

            ).setFlags(FLAG_ACTIVITY_NEW_TASK)
        )

        return START_NOT_STICKY
    }
}
