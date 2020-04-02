package com.bloom;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class mLocationListener implements LocationListener {

        private Location currentLoc;
        private genfencing gg;


        public mLocationListener(genfencing geo){
            gg = geo;
        }

        @Override
        public void onLocationChanged(Location loc){

            currentLoc = loc;
            gg.setCurrentloc(currentLoc);
            gg.locationChanged = "changed";
        }

        @Override
        public void onStatusChanged(String s, int in, Bundle bundle){

        }

        @Override
        public void onProviderEnabled(String s){

        }

        @Override
        public void onProviderDisabled(String s){

        }


        public double getTheLatitude(){
            return currentLoc.getLatitude();
        }

        public double getTheLongitude(){
            return currentLoc.getLongitude();
        }

}
