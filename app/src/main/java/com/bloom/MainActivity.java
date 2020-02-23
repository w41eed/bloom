package com.bloom;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.util.Log;


//timing the activity upon leaving app is importat. extend app compat may help with that.

public class MainActivity extends AppCompatActivity {
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open a new activity
                Intent intent = new Intent(MainActivity.this, MainHomepage.class);
                startActivity(intent);

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

    public void onActivityDestroyed() {
        Log.d("onDestroy", "onActivityDestroyed: ");
    }

}
