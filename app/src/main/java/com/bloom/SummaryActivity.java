package com.bloom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplication();
        TextView text_dead = findViewById(R.id.deadFlower_TextView);
        text_dead.setText("You have "+flowerClass.getDeadFlowerNum()+" withered sunflower in your garden");
        TextView text_healthy = findViewById(R.id.healthyFlower_TextView);
        text_healthy.setText("You have "+flowerClass.getAliveFlowerNum()+" healthy sunflower in your garden");

    }
}
