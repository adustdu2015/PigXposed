package com.example.pigxposed;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidModule {
    private final Application application;

    public AndroidModule(Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    Context applicationContext(){
        return application;
    }
    @Provides
    @Singleton
    LocationManager providedLocation(){
        return (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
    }
}
