package com.bloom;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class FlowerGlobalClass extends Application {
    private int DeadFlowerNum;
    private int AliveFlowerNum;
    public static final String CHANNEL_ID = "timerService";
    public static final String WAKE_LOCK_TAG = "app:myWakeLockTag";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

    }



    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Bloom",
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public int getDeadFlowerNum(){
        /*
        FileInputStream fin = null;
        try {
            fin = openFileInput("dead_flower");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int c = -1;
        String temp="";
        while(true){
            try {
                if (!((c = fin.read()) != -1)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = temp + (char) c;
        }
        this.DeadFlowerNum = Integer.parseInt(temp.trim());
        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return DeadFlowerNum;
    }

    public int getAliveFlowerNum(){
        /*
        FileInputStream fin = null;
        try {
            fin = openFileInput("alive_flower");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int c = -1;
        String temp="";
        while(true){
            try {
                if ((c = fin.read()) == -1) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            temp = temp + (char) c;
        }
        this.AliveFlowerNum = Integer.parseInt(temp.trim());
        try {
            fin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return AliveFlowerNum;
    }


    public void increaseDeadFlowerNum(){
        //this.DeadFlowerNum = this.getDeadFlowerNum();
        this.DeadFlowerNum ++;
        /*FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("dead_flower",MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String temp = Integer.toString(this.DeadFlowerNum);
        try {
            fOut.write(temp.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    public void increaseAliveFlowerNum(){
        //this.AliveFlowerNum = this.getAliveFlowerNum();
        this.AliveFlowerNum ++;
        /*FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("alive_flower",MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String temp = Integer.toString(this.AliveFlowerNum);
        try {
            fOut.write(temp.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
