package com.example.androidAlarm.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import timber.log.Timber

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Timber.d("bbbbbbbbbbbbbbbbbbbbb")
        Toast.makeText(p0, "Received ", Toast.LENGTH_LONG).show()
    }
}
