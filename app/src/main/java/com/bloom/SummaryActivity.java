package com.bloom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences myPrefs = getSharedPreferences("prefID", Context.MODE_PRIVATE);
        //String name = myPrefs.getString("nameKey","Default");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        //FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplication();
        TextView text_dead = findViewById(R.id.deadFlower_TextView);
        String dfname = myPrefs.getString("dead_flower",null);
        text_dead.setText("You have "+dfname+" withered sunflower in your garden");
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
