package com.bloom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainHomepage extends AppCompatActivity {

    private ArrayList<ToDoItem> ToDoList;
    private ToDoAdapter mAdapter;
    private Button NextButton;
    private navBarListener navBar;
    private Button LocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_homepage);

        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        NextButton = findViewById(R.id.button2);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a new activity
                Intent intent = new Intent(MainHomepage.this, Main2Activity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        LocationButton = findViewById(R.id.button_location);
        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a new activity
                Intent intent = new Intent(MainHomepage.this, genfencing.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        ViewPager viewPager = findViewById(R.id.viewpager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        initList();

        Spinner spinnertag = findViewById(R.id.spinner1);

        mAdapter = new ToDoAdapter(this, ToDoList);
        spinnertag.setAdapter(mAdapter);

        spinnertag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem clickedItem = (ToDoItem) parent.getItemAtPosition(position);
                String clickedItemName = clickedItem.getWhatToDo();

                SharedPreferences myPrefs;
                myPrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.putString("curr_tag", clickedItemName);//store the current last selected tag
                editor.commit();
                //Toast.makeText(MainHomepage.this, testdata + " selected", Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initList() {
        ToDoList = new ArrayList<>();
        ToDoList.add(new ToDoItem("STUDY", R.drawable.study));
        ToDoList.add(new ToDoItem("WORKOUT", R.drawable.workout));
        ToDoList.add(new ToDoItem("FUN", R.drawable.fun));
        ToDoList.add(new ToDoItem("MEETING", R.drawable.meeting));
        ToDoList.add(new ToDoItem("MEDITATION", R.drawable.meditation));
        ToDoList.add(new ToDoItem("SOCIAL", R.drawable.social));
        ToDoList.add(new ToDoItem("COOK", R.drawable.cook));
        ToDoList.add(new ToDoItem("HOUSEWORK", R.drawable.housework));
        ToDoList.add(new ToDoItem("OTHER", R.drawable.other));

    }



    //Geofence shit





}

