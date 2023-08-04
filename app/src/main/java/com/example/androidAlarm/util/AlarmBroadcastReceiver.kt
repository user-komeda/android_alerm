package com.example.androidAlarm.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.androidAlarm.model.ServiceState
import timber.log.Timber

class AlarmBroadcastReceiver : BroadcastReceiver() {

    val mediaPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p0 == null || p1 == null) {
            Timber.d("bbbbbbbbbbbbbbbbbbbbb")
            return
        }
        val intent: Intent = Intent(p0, MediaPlayerService::class.java)
        intent.putExtra("serviceState", p1.getStringExtra("serviceState"))
        p0.stopService(intent)
        if (p1.getStringExtra("serviceState").equals(ServiceState.STOP.name)) {
            p0.stopService(intent)
            return
        }
        Timber.d("bbbbbbbbbbbbbbbbbbbbb")
        Toast.makeText(p0, "Received ", Toast.LENGTH_LONG).show()

        p0.startForegroundService(intent)
    }
}
