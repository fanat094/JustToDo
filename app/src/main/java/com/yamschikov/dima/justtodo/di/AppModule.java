package com.yamschikov.dima.justtodo.di;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.Resources;

import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Application application) {
        this.context = application;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return this.context;
    }

    @Singleton
    @Provides
    public JustToDoDatabase provideMyDatabase(Context context) {
        return Room.databaseBuilder(context, JustToDoDatabase.class, "justtododb").build();
    }

    @Singleton
    @Provides
    public JustToDoDao provideUserDao(JustToDoDatabase justToDoDatabase) {
        return justToDoDatabase.justToDoDao();
    }
}