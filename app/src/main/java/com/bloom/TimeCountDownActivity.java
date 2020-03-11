package com.bloom;


import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import java.sql.Time;
import java.util.Locale;
import android.widget.Toast;
import android.content.Intent;
import static java.sql.Types.NULL;

import java.util.Timer;
import java.util.TimerTask;


public class TimeCountDownActivity extends AppCompatActivity implements TimerCancelDialog.TimerCancelDialogListener {

    private long INIT_TIMER_IN_MISECOND; // receive custom input
    private CountDownTimer Timer;
    private TextView CD_textview; //textview for 00:00
    private Button CD_startButton;
    private boolean CD_is_timer_running;
    private long CD_time_left_in_Misecond = INIT_TIMER_IN_MISECOND;
    private dndHandler dnd;
    private navBarListener navBar;

    private Timer detectAway;
    private TimerTask detectAwayTask;
    public boolean away;
    private final long COME_BACK_OR_ELSE_MS = 2000;
    private boolean on_break;

    // *** new stuff ***

    private Button CD_breakButton;
    private Timer schedule_timer; //timer for schedule tasks
    // **************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_down);
        CD_textview = findViewById(R.id.count_down_timer);
        CD_startButton = findViewById(R.id.button_start_timer);
        // *** new stuff ***
        CD_breakButton = findViewById(R.id.button_take_a_break);
        // *****************

        //Check for dnd access
        dnd = new dndHandler(this);
        dnd.checkDndPermission();

        //add navBar listener
        navBar = new navBarListener( (BottomNavigationView) findViewById(R.id.navBar), this);



        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get input time and display to clock
        Intent intent = getIntent();
        long ms_input = intent.getLongExtra(Main2Activity.MIN_INPUT,0);
        set_Time_CD(ms_input);


        CD_startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CD_is_timer_running){
                    warning_PopUp();
                }
                else {
                    start_Timer();
                }

            }
        });

        // *** new stuff ***
        CD_breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CD_is_timer_running){ //if timer is running while click break button: take a break for 5 mins
                    //if timer is already paused and click break button: nothing happens
                    take_a_break();

                }

            }
        });

        // *****************

    }

    private void start_Timer(){
        Timer = new CountDownTimer(CD_time_left_in_Misecond,1000) { //update every 1000ms = every 1s
            @Override
            public void onTick(long l) {
                CD_time_left_in_Misecond = l;
                //update count down text
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                CD_is_timer_running = false;
                //CD_time_left_in_Misecond = 0;
                CD_textview.setText("0:00:00");
                // when finish, a new sunflower is planted into the garden
                FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplicationContext();
                flowerClass.increaseAliveFlowerNum();

                //Turn off Do not Disturb after timer ends
                dnd.turnOffDnd();

                Intent intent = new Intent(TimeCountDownActivity.this, FlowerAliveActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //UpdateScreen();

            }
        }.start(); //as soon as start button is clicked,  new timer is created and start to count down
        CD_is_timer_running = true;

        //Turn on Do not Disturb after timer starts
        dnd.turnOnDnd();

        UpdateScreen();


    }

    // *** new stuff ***
    private void take_a_break(){
        //cancel the timer, create a new one after 5 minutes
        Timer.cancel();
        CD_is_timer_running = false;
        on_break = true;
        UpdateScreen();

        //after 5 minutes,restart timer(5 s for testing)
        schedule_timer = new Timer();
        schedule_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimeCountDownActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        on_break = false;
                        start_Timer();
                    }
                });
            }
        },10000);


    }
    // **************

    public void startDetectAwayTimer() {
        this.detectAway = new Timer();
        this.detectAwayTask = new TimerTask() {
            public void run() {
                TimeCountDownActivity.this.away = true;
            }
        };
        this.detectAway.schedule(detectAwayTask, COME_BACK_OR_ELSE_MS);
    }

    public void stopStartDetectAwayTimer() {
        if (this.detectAwayTask != null) this.detectAwayTask.cancel();
        if (this.detectAway != null) this.detectAway.cancel();
        this.away = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(CD_is_timer_running) {
            if (this.away && !on_break) {
                Timer.cancel();
                CD_is_timer_running = false;
                FlowerGlobalClass flowerClass = (FlowerGlobalClass) getApplicationContext();
                flowerClass.increaseDeadFlowerNum();

                dnd.turnOffDnd();

                Intent intent = new Intent(TimeCountDownActivity.this, FlowerDeadActivity.class);
                startActivity(intent);
            }
            this.stopStartDetectAwayTimer();
        }

        this.stopStartDetectAwayTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!on_break) this.startDetectAwayTimer();
    }

    private void warning_PopUp(){


        // UpdateScreen();
        TimerCancelDialog dialog = new TimerCancelDialog();
        dialog.show(getSupportFragmentManager(),"timer cancel dialog");

    }
    public void ClickYes(){
        Timer.cancel();
        CD_is_timer_running = false;
        set_Time_CD(0);
        FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplicationContext();

        flowerClass.increaseDeadFlowerNum();

        //Turn off Do not Disturb
        dnd.turnOffDnd();

        Intent intent = new Intent(TimeCountDownActivity.this, FlowerDeadActivity.class);
        startActivity(intent);
        overridePendingTransition(NULL, NULL);
    }
    //set input time to count down timer
    private void set_Time_CD(long ms){
        INIT_TIMER_IN_MISECOND = ms;
        reset_Timer();

    }
    private void reset_Timer(){
        CD_time_left_in_Misecond = INIT_TIMER_IN_MISECOND;
        updateCountDownText();
        // CD_resetButton.setVisibility(View.INVISIBLE);
        // CD_startButton.setVisibility(View.VISIBLE);

    }

    private void updateCountDownText(){
        RelativeLayout r = findViewById(R.id.nothing);
        if ((CD_time_left_in_Misecond < (0.75) * INIT_TIMER_IN_MISECOND) &&
                ((0.50) * INIT_TIMER_IN_MISECOND < CD_time_left_in_Misecond))
            r.setBackgroundResource(R.drawable.sunflower_level1);
        else if ( ((0.25) * INIT_TIMER_IN_MISECOND < CD_time_left_in_Misecond) &&
                (CD_time_left_in_Misecond < (0.50) * INIT_TIMER_IN_MISECOND))
            r.setBackgroundResource(R.drawable.sunflower_level2);
        else if ( 0 < CD_time_left_in_Misecond &&
                (CD_time_left_in_Misecond < (0.25) * INIT_TIMER_IN_MISECOND))
            r.setBackgroundResource(R.drawable.sunflower_level3);
        //count down in hours
        int hours = (int)(CD_time_left_in_Misecond/1000)/3600;
        //count down in minutes
        int mins = (int)((CD_time_left_in_Misecond/1000) % 3600) / 60;
        //count down in seconds
        int seconds = (int)(CD_time_left_in_Misecond/1000)%60;
        String time_shown = String.format(Locale.getDefault(), "%d:%02d:%02d", hours,mins, seconds);
        CD_textview.setText(time_shown);


    }
    private void UpdateScreen(){
        if (CD_is_timer_running){

            CD_startButton.setText("CANCEL");
            CD_startButton.setVisibility(View.VISIBLE);

        }
        else { //the only case for not running: time = 0
            // CD_startButton.setText("START");

            //time = 0
            //  if(CD_time_left_in_Misecond < 1000){
            CD_startButton.setVisibility(View.INVISIBLE);

            // } else {
            //  CD_startButton.setVisibility(View.VISIBLE);
            // }


        }
    }
}
