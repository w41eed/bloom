package com.bloom;

import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.RelativeLayout;

public class FlowerAliveActivity extends AppCompatActivity {
    private navBarListener navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_alive);
        SharedPreferences thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
        String f_type = thePrefs.getString("flower_type",null);
        //RelativeLayout dd = findViewById(R.id.dead);
        //setContentView(R.layout.activity_flower_dead);
        if(f_type == "f1") {

            RelativeLayout dd = findViewById(R.id.alive);
            //setContentView(R.layout.activity_flower_dead);
            dd.setBackgroundResource(R.drawable.sunflower_level3);
        }
        else if(f_type == "f2") {
            RelativeLayout dd = findViewById(R.id.alive);
            //RelativeLayout ll = new RelativeLayout(this);
            dd.setBackgroundResource(R.drawable.rose_level3);
            //setContentView(ll);

        }
        else if(f_type == "f3") {
            RelativeLayout dd = findViewById(R.id.alive);
            //RelativeLayout ll = new RelativeLayout(this);
            dd.setBackgroundResource(R.drawable.lily_level3);
            //setContentView(ll);

        }
        else{
            RelativeLayout dd = findViewById(R.id.alive);
            //setContentView(R.layout.activity_flower_dead);
            dd.setBackgroundResource(R.drawable.sunflower_level3);
        }

        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        Button Summary = (Button)findViewById(R.id.SummaryButton);

        Summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerAliveActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
