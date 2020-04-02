package com.bloom;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.location.LocationListener;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;


public class genfencing extends AppCompatActivity implements LabelDialog.LabelDialogListener {

    private RecyclerView m_recyclerView;
    private locationAdapter m_Adapter;
    private RecyclerView.LayoutManager m_layoutManager;
    private ImageView m_addLocation;
    private ArrayList<locationItem> location_list;

    private LocationListener mLocListener;
    private LocationManager mLocManager;
    private Location currentLoc;
    private long LOCATION_REFRESH_TIME = 300000;
    private float LOCATION_REFRESH_DISTANCE = 10;

    private FusedLocationProviderClient mFusedLocClient;

    public String locationChanged = "Hasn't changed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genfencing);
        location_list = new ArrayList<>();

        m_addLocation = findViewById(R.id.icon_addLoc);

        m_addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open dialog and get label information from users
                LabelDialog ld = new LabelDialog();
                ld.show(getSupportFragmentManager(), "label dialog");



            }
        });



        m_recyclerView = findViewById(R.id.recyclerview_location);
        m_recyclerView.setHasFixedSize(true);
        m_layoutManager = new LinearLayoutManager(this);
        m_Adapter = new locationAdapter(location_list);
        m_recyclerView.setLayoutManager(m_layoutManager); //pass the layout manager we created to recycler view
        m_recyclerView.setAdapter(m_Adapter);

        m_Adapter.setOnItemClickListener(new locationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                location_list.remove(pos);
                m_Adapter.notifyItemRemoved(pos);

            }
        });


        //Location stuff
        mFusedLocClient = LocationServices.getFusedLocationProviderClient(this);


        mFusedLocClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        /*if (location == null) {
                            requestNewLocationData();
                        } else{ currentLoc = location;}*/
                        requestNewLocationData();
                    }
                }
        );




    }


    //to check for nw location if current location is null
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }


    //When method returns from requestnewlocation
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            currentLoc = locationResult.getLastLocation();

        }
    };




    @Override
    public void addLabel(String label) {
        // for now just add a random location, later will be change to current location
        requestNewLocationData();

        String latlong = String.valueOf(currentLoc.getLatitude()).concat(" , ").concat(String.valueOf(currentLoc.getLongitude()));

        locationItem item = new locationItem(label, latlong );
        location_list.add(item);
        m_Adapter.notifyDataSetChanged();
    }


    public void setCurrentloc(Location loc){
        currentLoc = loc;
    }

}

