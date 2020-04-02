package com.bloom;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;


import java.util.Locale;
import android.widget.Toast;
import android.content.Intent;

import static com.bloom.FlowerGlobalClass.CHANNEL_ID;
import static com.bloom.FlowerGlobalClass.WAKE_LOCK_TAG;
import static java.sql.Types.NULL;

import java.util.Timer;
import java.util.TimerTask;

import static java.sql.Types.NULL;


public class TimeCountDownActivity extends AppCompatActivity implements TimerCancelDialog.TimerCancelDialogListener {

    private long INIT_TIMER_IN_MISECOND; // receive custom input
    private CountDownTimer Timer;
    private TextView CD_textview; //textview for 00:00
    private Button CD_startButton;
    private boolean CD_is_timer_running;
    private long CD_time_left_in_Misecond = INIT_TIMER_IN_MISECOND;
    private dndHandler dnd;
    private navBarListener navBarListen;
    private BottomNavigationView navBar;

    private Timer detectAway;
    private TimerTask detectAwayTask;
    public boolean away;
    private final long COME_BACK_OR_ELSE_MS = 2000;
    private boolean on_break;
    private boolean isScreenOn;



    private PowerManager pm;
    private PowerManager.WakeLock wl;

    // *** new stuff ***

    private Button CD_breakButton;
    private Timer schedule_timer; //timer for schedule tasks
    // **************
    SharedPreferences myPrefs; //= getSharedPreferences("prefID", MODE_PRIVATE);
    SharedPreferences.Editor editor; //editor = myPrefs.edit();;
    SharedPreferences thePrefs;//this is for tag page



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_down);

        //Change Color of StatusBar to match background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.skyBlueAlive));
        }


        CD_textview = findViewById(R.id.count_down_timer);
        CD_startButton = findViewById(R.id.button_start_timer);
        // *** new stuff ***
        CD_breakButton = findViewById(R.id.button_take_a_break);
        // *****************
        myPrefs = getSharedPreferences("prefID", MODE_PRIVATE);
        //!sharedPreferences.getString(NAME,"Default value").equals("Default value")
        if(myPrefs.getString("alive_flower","Infinite").equals( "Infinite")) {
            editor = myPrefs.edit();

            editor.putString("alive_flower", "0");
            editor.commit();
            editor.putString("dead_flower", "0");
            editor.commit();
            //editor.putString("modify", "yes");
            //editor.commit();
        }

        //Check for dnd access
        dnd = new dndHandler(this);
        dnd.checkDndPermission();

        //add navBar listener
        navBar = (BottomNavigationView) findViewById(R.id.navBar);
        navBarListen = new navBarListener( navBar, this);

        //Initialize stuff for Wake Lock
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        //wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_TAG);
        isScreenOn = pm.isInteractive();


        //Change Status bar color to match background
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get input time and display to clock
        Intent intent = getIntent();
        long ms_input = intent.getLongExtra(Main2Activity.MIN_INPUT,0);
        set_Time_CD(ms_input);
        long min_input = ms_input/60000; // convert the input back to minites
        SharedPreferences myPrefs;
        myPrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putLong("set_time", min_input);//store the time input in this round no matter finish or not
        editor.commit();

        //Start button listener (If timer is running then start button gets converted to Cancel button
        CD_startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CD_is_timer_running){
                    warning_PopUp();
                }
                else {
                    start_Timer();

                    //Start Service so app runs in background
                    //Intent servIntent = new Intent(TimeCountDownActivity.this, timerService.class);


                    //acquire Wake lock
                    //wl.acquire();

                }

            }
        });

        // Break Button Listener
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


    //When Timer Starts, this shit happens
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
                //FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplicationContext();///////////////
                //flowerClass.increaseAliveFlowerNum();/////////////////

                myPrefs = getSharedPreferences("prefID", MODE_PRIVATE);
                String fnum = myPrefs.getString("alive_flower", null);
                int num = Integer.parseInt(fnum.trim()) + 1;
                editor = myPrefs.edit();
                editor.putString("alive_flower", Integer.toString(num));
                editor.apply();
                thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
                long time_num = thePrefs.getLong("set_time",0);
                String tagnow = thePrefs.getString("curr_tag",null);
                long curr_tag_time = thePrefs.getLong(tagnow,0);
                editor = thePrefs.edit();
                if(curr_tag_time == 0){ //first time to select this tag
                 editor.putLong(tagnow,time_num);
                 editor.commit();
                } else{ //select this tag before
                 long total_tag_time = curr_tag_time + time_num;
                 editor.putLong(tagnow, total_tag_time);
                 editor.commit();
                }



                //Turn off Do not Disturb after timer ends
                dnd.turnOffDnd();


                //Make NavBar visible
                navBar.setVisibility(View.VISIBLE);

                sleep(1000);
                //Send Notification
                finishNotification();

                Intent intent = new Intent(TimeCountDownActivity.this, FlowerAliveActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                //UpdateScreen();

            }
        }.start(); //as soon as start button is clicked,  new timer is created and start to count down
        CD_is_timer_running = true;

        //Turn on Do not Disturb after timer starts
        dnd.turnOnDnd();

        //Make NavBar Invisible
        navBar.setVisibility(View.INVISIBLE);

        UpdateScreen();


    }

    // When you hit dat break button
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

    //Timer starts to keep track for how long the user has left the activity
    public void startDetectAwayTimer() {
        this.detectAway = new Timer();
        this.detectAwayTask = new TimerTask() {
            public void run() {
                TimeCountDownActivity.this.away = true;
            }
        };
        this.detectAway.schedule(detectAwayTask, COME_BACK_OR_ELSE_MS);
    }


    //Stop timer that starts when user leaves the activity
    public void stopStartDetectAwayTimer() {
        if (this.detectAwayTask != null) this.detectAwayTask.cancel();
        if (this.detectAway != null) this.detectAway.cancel();
        this.away = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        //isScreenOn = pm.isInteractive();
        if(CD_is_timer_running) {
            if (this.away && !on_break && isScreenOn) {
                Timer.cancel();
                CD_is_timer_running = false;
                //FlowerGlobalClass flowerClass = (FlowerGlobalClass) getApplicationContext();
                //flowerClass.increaseDeadFlowerNum();
                myPrefs = getSharedPreferences("prefID", MODE_PRIVATE);
                String fnum = myPrefs.getString("alive_flower",null);
                int num = Integer.parseInt(fnum.trim()) + 1;
                editor = myPrefs.edit();
                editor.putString("alive_flower", Integer.toString(num));
                editor.apply();
                dnd.turnOffDnd();

                //Release WakeLock
                //wl.release();


                Intent intent = new Intent(TimeCountDownActivity.this, FlowerDeadActivity.class);
                startActivity(intent);
            }
            dnd.turnOnDnd();
            this.stopStartDetectAwayTimer();
        }

        this.stopStartDetectAwayTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        isScreenOn = pm.isInteractive();
        if (!on_break) this.startDetectAwayTimer();
    }

    @Override
    public void onStop() {
        isScreenOn = pm.isInteractive();
        super.onStop();
        dnd.turnOffDnd();
    }


    //When user wants to cancel the timer and murder a flower
    private void warning_PopUp(){


        // UpdateScreen();
        TimerCancelDialog dialog = new TimerCancelDialog();
        dialog.show(getSupportFragmentManager(),"timer cancel dialog");

    }

    //When user clicks Yes on the cancel dialog
    public void ClickYes(){
        Timer.cancel();
        CD_is_timer_running = false;
        set_Time_CD(0);
        //FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplicationContext();//////////////////////

        //flowerClass.increaseDeadFlowerNum();///////////////
        //SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        myPrefs = getSharedPreferences("prefID", MODE_PRIVATE);
        String fnum = myPrefs.getString("dead_flower",null);
        int num = Integer.parseInt(fnum) + 1;
        editor = myPrefs.edit();
        editor.putString("dead_flower", Integer.toString(num));
        editor.commit();

        //Turn off Do not Disturb
        dnd.turnOffDnd();
        //Make NavBar visible
        navBar.setVisibility(View.VISIBLE);

        //Release lock
        //wl.release();

        Intent intent = new Intent(TimeCountDownActivity.this, FlowerDeadActivity.class);
        startActivity(intent);
        finish();
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

    //Updates the screen backgrounds and timer view when timer is running
    private void updateCountDownText(){
        SharedPreferences thePrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
        String f_type = thePrefs.getString("flower_type",null);
        RelativeLayout r = findViewById(R.id.nothing);
        if ((CD_time_left_in_Misecond < (0.75) * INIT_TIMER_IN_MISECOND) &&
                ((0.50) * INIT_TIMER_IN_MISECOND < CD_time_left_in_Misecond)) {
            if (f_type == "f1") {
                r.setBackgroundResource(R.drawable.sunflower_level1);
            } else if (f_type == "f2") {
                r.setBackgroundResource(R.drawable.rose_level1);
            }
            else if (f_type == "f3") {
                r.setBackgroundResource(R.drawable.lily_level1);
            }
            else{
                r.setBackgroundResource(R.drawable.sunflower_level1);
            }
        }
        else if ( ((0.25) * INIT_TIMER_IN_MISECOND < CD_time_left_in_Misecond) &&
                (CD_time_left_in_Misecond < (0.50) * INIT_TIMER_IN_MISECOND)) {
            if (f_type == "f1") {
                r.setBackgroundResource(R.drawable.sunflower_level2);
            } else if (f_type == "f2") {
                r.setBackgroundResource(R.drawable.rose_level2);
            } else if (f_type == "f3") {
                r.setBackgroundResource(R.drawable.lily_level2);
            }
            else{
                r.setBackgroundResource(R.drawable.sunflower_level2);
            }
        }
        else if ( 0 < CD_time_left_in_Misecond &&
                (CD_time_left_in_Misecond < (0.25) * INIT_TIMER_IN_MISECOND)) {
            if (f_type == "f1") {
                r.setBackgroundResource(R.drawable.sunflower_level3);
            } else if (f_type == "f2") {
                r.setBackgroundResource(R.drawable.rose_level3);
            } else if (f_type == "f3") {
                r.setBackgroundResource(R.drawable.lily_level3);
            }
            else{
                r.setBackgroundResource(R.drawable.sunflower_level3);
            }

        }
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



    //Send Notification to user that timer has ended
    public void finishNotification(){

        //This notification will only be sent if timer ends successfully,
        //so tapping it should take you to the flower alive activity
        Intent intent = new Intent(this, FlowerAliveActivity.class);
        PendingIntent notifIntent = PendingIntent.getActivity(this,0,intent,0);

        //Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notif_flower)
                .setContentTitle("Your Flower has Bloomed")
                .setContentText("Tap to view your Flower")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notifIntent)
                .setAutoCancel(true);

        //Show Notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }


    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}
