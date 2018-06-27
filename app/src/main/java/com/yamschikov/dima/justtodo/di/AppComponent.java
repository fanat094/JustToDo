package com.yamschikov.dima.justtodo.di;

import com.yamschikov.dima.justtodo.addtask.AddTaskPresenter;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})

public interface AppComponent {

  void inject(AddTaskPresenter addTaskPresenter);
}