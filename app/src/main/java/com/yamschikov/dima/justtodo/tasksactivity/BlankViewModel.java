package com.yamschikov.dima.justtodo.tasksactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import java.util.List;

import javax.inject.Inject;

public class BlankViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    @Inject
    JustToDoDao justToDoDao;

    private MutableLiveData<List<JustToDoStructureTable>> data;
    private LiveData<List<JustToDoStructureTable>> dataList;

    public BlankViewModel() {
        BaseApplication.getAppComponent().inject(this);
    }

    //query
    public LiveData<List<JustToDoStructureTable>> getMedia() {
        return justToDoDao.getAllLiveData();
    }
}