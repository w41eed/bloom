package com.bloom;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    private navBarListener navBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        SharedPreferences thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_summary);

        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplication();
        TextView text_dead = findViewById(R.id.deadFlower_TextView);
        String dfname = myPrefs.getString("dead_flower",null);
        String taggg = thePrefs.getString("curr_tag",null);//get the current chosen tag
        long total_taggg_time = thePrefs.getLong(taggg,0);
        text_dead.setText("You have "+dfname+" withered sunflower in your garden"/*+"modify"+mmname
        +" "+taggg + " : "+total_taggg_time*/);
        TextView text_healthy = findViewById(R.id.healthyFlower_TextView);
        String afname = myPrefs.getString("alive_flower",null);
        text_healthy.setText("You have "+afname+" healthy sunflower in your garden");

    }

    public void goHome(View view){
        Intent intent = new Intent(SummaryActivity.this, MainHomepage.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }



}
