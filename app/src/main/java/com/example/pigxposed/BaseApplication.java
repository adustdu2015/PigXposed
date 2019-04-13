package com.example.pigxposed;

import android.app.Application;

import com.aitangba.swipeback.ActivityLifecycleHelper;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
    }
}
