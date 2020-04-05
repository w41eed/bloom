package com.bloom;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.bloom.FlowerGlobalClass.CHANNEL_ID;

public class timerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification notif = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Bloom")
                .setContentText("Bloom is running in the background")
                .setSmallIcon(R.drawable.home_button)
                .build();


        startForeground(1, notif);

        return START_STICKY;
    }
}
