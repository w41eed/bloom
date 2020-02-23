package com.bloom;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;

public class bloom extends Application implements Application.ActivityLifecycleCallbacks {
    private Handler handler;
    private Runnable toWilt = new Runnable() {
        @Override
        public void run() {
            //some toWilt();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        handler = new Handler(getMainLooper());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //toWilt();
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle out) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public void onUserLeaveHint(Activity activity) {

    }
}
