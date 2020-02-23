package com.bloom;

import android.app.Application;

public class FlowerGlobalClass extends Application {
    private int DeadFlowerNum;
    private int AliveFlowerNum;

    public int getDeadFlowerNum(){
        return DeadFlowerNum;
    }
    public int getAliveFlowerNum(){
        return AliveFlowerNum;
    }
    public void increaseDeadFlowerNum(){
        this.DeadFlowerNum = DeadFlowerNum + 1;
    }
    public void increaseAliveFlowerNum(){
        this.AliveFlowerNum = AliveFlowerNum + 1;
    }
}
