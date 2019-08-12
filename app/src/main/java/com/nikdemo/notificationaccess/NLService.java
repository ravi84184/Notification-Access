package com.nikdemo.notificationaccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NLService extends NotificationListenerService {
    private String TAG = "NLService";
    private NLServiceReceiver nlservicereciver;
    private  DataBaseHelper dbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DataBaseHelper(this);
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {



        if(sbn.getPackageName().equals("com.whatsapp.w4b") || sbn.getPackageName().equals("com.whatsapp")){
            Log.e(TAG,"**********  onNotificationPosted");
            Log.e(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
            Log.e(TAG,"Message :" + sbn.getNotification().extras.getString("android.text"));
            Log.e(TAG,"Title :" + sbn.getNotification().extras.getString("android.title"));
            Log.e(TAG,"Bundle :" + sbn.getNotification().extras);
            dbHelper.addUser(sbn.getNotification().extras.getString("android.title"),
                    sbn.getPackageName());

        }

        Intent i = new Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationPosted :" + sbn.getPackageName() + "\n");
        sendBroadcast(i);

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.e(TAG,"********** onNOtificationRemoved");
        Log.e(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event","=====================");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("com.nikdemo.notificationaccess.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event","===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

}
