package com.bloom;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    private navBarListener navBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_summary);

        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplication();
        TextView text_dead = findViewById(R.id.deadFlower_TextView);
        text_dead.setText("You have "+flowerClass.getDeadFlowerNum()+" withered sunflower in your garden");
        TextView text_healthy = findViewById(R.id.healthyFlower_TextView);
        text_healthy.setText("You have "+flowerClass.getAliveFlowerNum()+" healthy sunflower in your garden");

    }

    public void goHome(View view){
        Intent intent = new Intent(SummaryActivity.this, MainHomepage.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }



}
