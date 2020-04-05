package com.bloom;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

//timing the activity upon leaving app is importat. extend app compat may help with that.

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private navBarListener navBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_main);

        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        startButton = findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a new activity
                Intent intent = new Intent(MainActivity.this, MainHomepage.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
    long userInteractionTime = 0;

    public void onUserLeaveHint() {
        long uiDelta = (System.currentTimeMillis() - userInteractionTime);
        long uiLag = 5000;
        super.onUserLeaveHint();
        Log.i("There","Last User Interaction = "+uiLag);
        if (uiDelta < 100)
            Log.i("Bloom","To home");
        else
            Log.i("Bloom","Leave app rn...");
    }

    public void onUserInteraction() {
        userInteractionTime = System.currentTimeMillis();
        super.onUserInteraction();
        Log.i("Bloom","Interaction");
    }
}
