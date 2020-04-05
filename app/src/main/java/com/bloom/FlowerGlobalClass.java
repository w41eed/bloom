package com.bloom;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Build;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;


public class FlowerGlobalClass extends Application {
    public static final String CHANNEL_ID = "timerService";
    public static final String WAKE_LOCK_TAG = "app:myWakeLockTag";

    private GeofencingClient geofencingClient;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        geofencingClient = LocationServices.getGeofencingClient(this);
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Bloom",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private PendingIntent createGeofencePendingIntent() {
        if ( geoFencePendingIntent != null )
            return geoFencePendingIntent;
        Intent intent = new Intent( this, GeofenceReceiver.class);
        return PendingIntent.getBroadcast(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT );
    }


    public void addGeofence(Location loc){

        Geofence geofence = new Geofence.Builder()
                .setRequestId("test") // Geofence ID
                .setCircularRegion( loc.getLatitude(), loc.getLongitude(), 50) // defining fence region
                .setExpirationDuration(86400000) // expiring date
                // Transition types that it should look for
                .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT )
                .setLoiteringDelay(10000)
                .build();

        GeofencingRequest request = new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();

        createGeofencePendingIntent();
    }
}
