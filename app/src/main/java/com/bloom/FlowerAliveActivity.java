package com.bloom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class FlowerAliveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_alive);

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
