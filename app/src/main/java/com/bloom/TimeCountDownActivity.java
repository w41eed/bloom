package com.bloom;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
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

    private Timer detectAway;
    private TimerTask detectAwayTask;
    public boolean away;
    private final long COME_BACK_OR_ELSE_MS = 2000;
    SharedPreferences myPrefs; //= getSharedPreferences("prefID", MODE_PRIVATE);
    SharedPreferences.Editor editor; //editor = myPrefs.edit();;
    SharedPreferences thePrefs;//this is for tag page



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_down);
        CD_textview = findViewById(R.id.count_down_timer);
        CD_startButton = findViewById(R.id.button_start_timer);
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

                Intent intent = new Intent(TimeCountDownActivity.this, FlowerAliveActivity.class);
                startActivity(intent);
                overridePendingTransition(NULL, NULL);
                //UpdateScreen();

            }
        }.start(); //as soon as start button is clicked,  new timer is created and start to count down
        CD_is_timer_running = true;

        //Turn on Do not Disturb after timer starts
        dnd.turnOnDnd();

        UpdateScreen();


    }

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
        if (this.away) {
            Timer.cancel();
            CD_is_timer_running = false;
            //FlowerGlobalClass flowerClass = (FlowerGlobalClass)getApplicationContext();////////////////
            //flowerClass.increaseDeadFlowerNum();//////////////
            myPrefs = getSharedPreferences("prefID", MODE_PRIVATE);
            String fnum = myPrefs.getString("alive_flower",null);
            int num = Integer.parseInt(fnum.trim()) + 1;
            editor = myPrefs.edit();
            editor.putString("alive_flower", Integer.toString(num));
            editor.apply();

            Intent intent = new Intent(TimeCountDownActivity.this, FlowerDeadActivity.class);
            startActivity(intent);
        }
        this.stopStartDetectAwayTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.startDetectAwayTimer();
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
