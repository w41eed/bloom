package com.bloom;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import static com.bloom.FlowerGlobalClass.CHANNEL_ID;


public class GeofenceReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent){
        this.context = context;
        geofenceNotification();
    }

    //Send Notification to user asking if they want to start a timer
    public void geofenceNotification(){
        //This notification will only be sent if timer ends successfully,
        //so tapping it should take you to the flower alive activity
        Intent intent = new Intent(context, MainHomepage.class);
        PendingIntent notifIntent = PendingIntent.getActivity(context,0,intent,0);
        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notif_flower)
                .setContentTitle("Would you like to start Bloom")
                .setContentText("Tap to start the timer")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifIntent)
                .setAutoCancel(true);
        //Show Notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }

}
