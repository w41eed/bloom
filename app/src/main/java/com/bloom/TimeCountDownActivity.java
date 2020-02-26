package com.bloom;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import java.util.Locale;
import android.content.Intent;
import static java.sql.Types.NULL;


public class TimeCountDownActivity extends AppCompatActivity implements TimerCancelDialog.TimerCancelDialogListener {

    private long INIT_TIMER_IN_MISECOND; // receive custom input
    private CountDownTimer Timer;
    private TextView CD_textview; //textview for 00:00
    private Button CD_startButton;
    private boolean CD_is_timer_running;
    private long CD_time_left_in_Misecond = INIT_TIMER_IN_MISECOND;
    private dndHandler dnd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count_down);
        CD_textview = findViewById(R.id.count_down_timer);
        CD_startButton = findViewById(R.id.button_start_timer);

        //Check for dnd access
        dnd = new dndHandler(this);
        dnd.checkDndPermission();





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                overridePendingTransition(NULL, NULL);
                //UpdateScreen();

            }
        }.start(); //as soon as start button is clicked,  new timer is created and start to count down
        CD_is_timer_running = true;

        //Turn on Do not Disturb after timer starts
        dnd.turnOnDnd();

        UpdateScreen();


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
