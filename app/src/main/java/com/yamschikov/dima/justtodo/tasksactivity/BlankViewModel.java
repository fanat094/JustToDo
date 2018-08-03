package com.yamschikov.dima.justtodo.tasksactivity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.TaskRepository;

import java.util.List;

import javax.inject.Inject;

public class BlankViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    @Inject
    JustToDoDao justToDoDao;

    private TaskRepository mRepository;

    private MutableLiveData<List<JustToDoStructureTable>> data;
    private LiveData<List<JustToDoStructureTable>> dataList;

    public BlankViewModel(Application application) {
        super(application);
        BaseApplication.getAppComponent().inject(this);
        mRepository = new TaskRepository();
    }

    public void deleteTask(JustToDoStructureTable justToDoStructureTable) {
        mRepository.deleteTask(justToDoStructureTable);
    }

    public void insert(JustToDoStructureTable justToDoStructureTable) {
        mRepository.insert(justToDoStructureTable);
    }

    //delete param
    public void deleteAll(String userId) {
        mRepository.deleteAll(userId);
    }

    public void deleteTaskByCategory(String userId, String taskCategory) {
        mRepository.deleteTaskByCategory(userId, taskCategory);
    }

    public void deleteTaskByToday(String userId, String taskDate) {
        mRepository.deleteTaskByToday(userId, taskDate);
    }

    //query param
    public LiveData<List<JustToDoStructureTable>> getTasksToday(String task_date, String task_user_id) {
        return justToDoDao.getTasksTodayLiveData(task_date, task_user_id);
    }

    public LiveData<List<JustToDoStructureTable>> getAllTasks(String task_user_id) {
        return justToDoDao.getAllTasksLiveData(task_user_id);
    }

    public LiveData<List<JustToDoStructureTable>> getTasksHome(String task_kategory, String task_user_id) {
        return justToDoDao.getTasksHomeLiveData(task_kategory, task_user_id);
    }

    public LiveData<List<JustToDoStructureTable>> getTasksWork(String task_kategory, String task_user_id) {
        return justToDoDao.getTasksWorkLiveData(task_kategory, task_user_id);
    }

    public LiveData<List<JustToDoStructureTable>> getTasksPersonal(String task_kategory, String task_user_id) {
        return justToDoDao.getTasksPersonalLiveData(task_kategory, task_user_id);
    }

    public LiveData<List<JustToDoStructureTable>> getTasksStore(String task_kategory, String task_user_id) {
        return justToDoDao.getTasksStoreLiveData(task_kategory, task_user_id);
    }




    //old
    public LiveData<List<JustToDoStructureTable>> getAllTask() {
        return justToDoDao.getAllLiveData();
    }
}