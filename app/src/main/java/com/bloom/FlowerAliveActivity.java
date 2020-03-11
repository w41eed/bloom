package com.bloom;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class FlowerAliveActivity extends AppCompatActivity {
    private navBarListener navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_alive);
        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        Button GoBackToSetTime = (Button) findViewById(R.id.btn_goBackToSetTime);
        Button Summary = (Button)findViewById(R.id.SummaryButton);
        GoBackToSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerAliveActivity.this, MainHomepage.class);
                startActivity(intent);
            }
        }
        );
        Summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerAliveActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });
    }
}
