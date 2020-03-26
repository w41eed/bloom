package com.bloom;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;


public class genfencing extends AppCompatActivity implements LabelDialog.LabelDialogListener {

    private RecyclerView m_recyclerView;
    private locationAdapter m_Adapter;
    private RecyclerView.LayoutManager m_layoutManager;
    private ImageView m_addLocation;
    private ArrayList<locationItem> location_list;

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


    }

    @Override
    public void addLabel(String label) {
        // for now just add a random location, later will be change to current location
        locationItem item = new locationItem(label, "Current Location");
        location_list.add(item);
        m_Adapter.notifyDataSetChanged();
    }
}
