package com.yamschikov.dima.justtodo;

import android.app.Application;

import com.yamschikov.dima.justtodo.di.AppComponent;
import com.yamschikov.dima.justtodo.di.AppModule;
import com.yamschikov.dima.justtodo.di.DaggerAppComponent;


public class BaseApplication extends Application{

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}