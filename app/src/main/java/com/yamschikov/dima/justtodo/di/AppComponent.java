package com.yamschikov.dima.justtodo.di;

import com.yamschikov.dima.justtodo.addtask.AddTaskPresenter;
import com.yamschikov.dima.justtodo.tasksactivity.BlankViewModel;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskToday;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})

public interface AppComponent {

  void inject(AddTaskPresenter addTaskPresenter);
  void inject(FragmentTaskToday fragmentTaskToday);
  void inject(BlankViewModel blankViewModel);
}