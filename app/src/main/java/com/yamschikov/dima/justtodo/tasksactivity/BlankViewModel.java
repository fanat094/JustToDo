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

    //query
    public LiveData<List<JustToDoStructureTable>> getAllTask() {
        return justToDoDao.getAllLiveData();
    }

    public void deleteTask(JustToDoStructureTable justToDoStructureTable) {
        mRepository.deleteTask(justToDoStructureTable);
    }

    public void insert(JustToDoStructureTable justToDoStructureTable) {
        mRepository.insert(justToDoStructureTable);
    }
}