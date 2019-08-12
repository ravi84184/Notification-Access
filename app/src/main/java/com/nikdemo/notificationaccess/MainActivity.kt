package com.nikdemo.notificationaccess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.provider.Settings
import android.view.View


class MainActivity : AppCompatActivity() {

    private var nReceiver: NotificationReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
470277
        nReceiver = NotificationReceiver()
        val filter = IntentFilter()
        filter.addAction("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_EXAMPLE")
        registerReceiver(nReceiver, filter)

        if (Settings.Secure.getString(this.contentResolver,"enabled_notification_listeners").contains(applicationContext.packageName))
        {
            //service is enabled do something
        } else {
            //service is not enabled try to enabled by calling...
            applicationContext.startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(nReceiver)
    }
    fun buttonClicked(v: View) {

        if (v.getId() == R.id.btnCreateNotify) {
            val nManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val ncomp = NotificationCompat.Builder(this)
            ncomp.setContentTitle("My Notification")
            ncomp.setContentText("Notification Listener Service Example")
            ncomp.setTicker("Notification Listener Service Example")
            ncomp.setSmallIcon(R.mipmap.ic_launcher)
            ncomp.setAutoCancel(true)
            nManager.notify(System.currentTimeMillis().toInt(), ncomp.build())
        } else if (v.getId() == R.id.btnClearNotify) {
            val i = Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
            i.putExtra("command", "clearall")
            sendBroadcast(i)
        } else if (v.getId() == R.id.btnListNotify) {
            val i = Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_SERVICE_EXAMPLE")
            i.putExtra("command", "list")
            sendBroadcast(i)
        }


    }

    internal inner class NotificationReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val temp = intent.getStringExtra("notification_event") + "\n" + textView!!.getText()
            textView!!.setText(temp)
        }
    }
}
