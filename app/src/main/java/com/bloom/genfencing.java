package com.bloom;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import  com.bloom.FlowerGlobalClass;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class genfencing extends AppCompatActivity implements LabelDialog.LabelDialogListener {

    private RecyclerView m_recyclerView;
    private locationAdapter m_Adapter;
    private RecyclerView.LayoutManager m_layoutManager;
    private ImageView m_addLocation;
    private ArrayList<locationItem> location_list;

    private LocationManager mLocManager;
    private Location currentLoc;
    private long LOCATION_REFRESH_TIME = 300000;
    private float LOCATION_REFRESH_DISTANCE = 10;

    private FusedLocationProviderClient mFusedLocClient;
    private FlowerGlobalClass fgc;


    private Geocoder geocoder;
    private List<Address> address = null;

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

        fgc = (FlowerGlobalClass) getApplicationContext();

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
    private void requestNewLocationData() {

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




    private String getAddress() {


        geocoder = new Geocoder(this, Locale.getDefault());


        try {
            address = geocoder.getFromLocation(currentLoc.getLatitude(), currentLoc.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (Exception e) { }


        String Labeladdress = address.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        //String city = address.get(0).getLocality();

        return Labeladdress;

    }

    @Override
    public void addLabel(String label) {
        // for now just add a random location, later will be change to current location
        requestNewLocationData();

         fgc.addGeofence(currentLoc);

        String latlong = String.valueOf(currentLoc.getLatitude()).concat(" , ").concat(String.valueOf(currentLoc.getLongitude()));

        locationItem item = new locationItem(label, getAddress());
        location_list.add(item);
        m_Adapter.notifyDataSetChanged();
    }



}

