package com.hoamz.a21_5.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.hoamz.a21_5.R

/**
 * @author hwa..
 */
class MyService : Service() {
    private val CLASS_NAME = javaClass.simpleName

    override fun onCreate() {
        super.onCreate()
        Log.e(CLASS_NAME, "Service da duoc khoi tao")
    }

    //test
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val intentData = intent?.getStringExtra("SV") ?: "default val"
        Log.e(CLASS_NAME, intentData)
        createNotificationChannel()
        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Test foreground service")
                .setContentText("This is a mock data $intentData").setOngoing(true)
                .setSmallIcon(R.drawable.ic_logo).setPriority(NotificationCompat.PRIORITY_MAX)
                .build()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            ServiceCompat.startForeground(
                this, 1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        } else {
            startForeground(1, notification)
        }
        return START_STICKY
    }

    private val CHANNEL_ID = "fg_service_id"
    private val CHANNEL_NAME = "Test foreground service"
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(CLASS_NAME, "Service da bi huy")
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}