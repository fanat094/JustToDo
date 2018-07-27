package com.yamschikov.dima.justtodo.di;

import com.yamschikov.dima.justtodo.WelcomeActivity;
import com.yamschikov.dima.justtodo.addtask.AddTaskActivity;
import com.yamschikov.dima.justtodo.tasksactivity.BlankViewModel;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskAllTasks;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskHome;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskPersonal;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskStore;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskToday;
import com.yamschikov.dima.justtodo.tasksactivity.FragmentTaskWork;
import com.yamschikov.dima.justtodo.tasksactivity.TasksActivity;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.TaskRepository;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, SharedPreferencesModule.class})

public interface AppComponent {

  void inject(WelcomeActivity welcomeActivity);
  void inject(TasksActivity tasksActivity);
  void inject(AddTaskActivity addTaskActivity);
  void inject(BlankViewModel blankViewModel);
  void inject(TaskRepository taskRepository);

  void inject(FragmentTaskToday fragmentTaskToday);
  void inject(FragmentTaskAllTasks fragmentTaskAllTasks);
  void inject(FragmentTaskHome fragmentTaskHome);
  void inject(FragmentTaskWork fragmentTaskWork);
  void inject(FragmentTaskPersonal fragmentTaskPersonal);
  void inject(FragmentTaskStore fragmentTaskStore);
}