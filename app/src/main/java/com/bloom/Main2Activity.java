package com.bloom;

import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class Main2Activity extends AppCompatActivity {
    private navBarListener navBar;


    public static final String MIN_INPUT = "com.bloom.min_input";
    private SeekBar min_seekBar;
    private TextView min_textView;
    private Button CD_setMinButton;
    private dndHandler dnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        min_seekBar = findViewById(R.id.minute_seekBar);
        min_textView = findViewById(R.id.minute_textView);
        CD_setMinButton = findViewById(R.id.minute_button_set);

        //Change Color of StatusBar to match background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.skyBlueAlive));
        }


        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);

        //dnd = new dndHandler(this);
        //dnd.checkDndPermission();

        min_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                min_textView.setText((i*10)+":00");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        CD_setMinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity();
            }
        });


    }

    public void openNextActivity(){
        Intent intent = new Intent(Main2Activity.this, TimeCountDownActivity.class);
        min_seekBar = findViewById(R.id.minute_seekBar);

        int seekBarValue = min_seekBar.getProgress();

        //check if user enter some input
        if (seekBarValue == 0){
            Toast.makeText(Main2Activity.this, "Please choose a time", Toast.LENGTH_SHORT).show();
            return;
        }
        //receive input and convert minutes to ms
        long ms_input = (long)seekBarValue * 600 * 1000;


        intent.putExtra(MIN_INPUT, ms_input);
        startActivity(intent);
        overridePendingTransition(NULL,NULL);
    }
}

