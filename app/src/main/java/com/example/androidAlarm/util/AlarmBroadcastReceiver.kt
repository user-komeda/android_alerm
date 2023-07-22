package com.example.androidAlarm.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import timber.log.Timber

class AlarmBroadcastReceiver : BroadcastReceiver() {

    var mediaPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0 == null || p1 == null) {
            return
        }
        Timber.d("bbbbbbbbbbbbbbbbbbbbb")
        Toast.makeText(p0, "Received ", Toast.LENGTH_LONG).show()

        val intent: Intent = Intent(p0, MediaPlayerService::class.java)
        p0.startForegroundService(intent)
    }
}
