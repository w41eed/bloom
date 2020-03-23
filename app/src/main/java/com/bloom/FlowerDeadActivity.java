package com.bloom;

import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class FlowerDeadActivity extends AppCompatActivity {
    private navBarListener navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_dead);
        SharedPreferences thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
        String f_type = thePrefs.getString("flower_type",null);
        //RelativeLayout dd = findViewById(R.id.dead);
        //setContentView(R.layout.activity_flower_dead);
        if(f_type == "f1") {

            RelativeLayout dd = findViewById(R.id.dead);
            //setContentView(R.layout.activity_flower_dead);
            dd.setBackgroundResource(R.drawable.sunflower_dead);
        }
        else if(f_type == "f2") {
            RelativeLayout dd = findViewById(R.id.dead);
            //RelativeLayout ll = new RelativeLayout(this);
            dd.setBackgroundResource(R.drawable.rose_dead);
            //setContentView(ll);
        }else if(f_type == "f3") {
                RelativeLayout dd = findViewById(R.id.dead);
                //RelativeLayout ll = new RelativeLayout(this);
                dd.setBackgroundResource(R.drawable.lily_dead);
                //setContentView(ll);

            }

        //Change Color of StatusBar to match background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.skyBlueDead));
        }
        else {
            RelativeLayout dd = findViewById(R.id.dead);
            //setContentView(R.layout.activity_flower_dead);
            dd.setBackgroundResource(R.drawable.sunflower_dead);
        }

        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

    }
}
