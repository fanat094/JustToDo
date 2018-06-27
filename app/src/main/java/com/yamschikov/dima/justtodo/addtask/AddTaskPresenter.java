package com.yamschikov.dima.justtodo.addtask;

import android.os.AsyncTask;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import javax.inject.Inject;

public class AddTaskPresenter {


    AddTaskView view;

    @Inject
    JustToDoDao justToDoDao;


    public void attachView(AddTaskView view) {
        this.view = view;
    }

    public AddTaskPresenter() {

        BaseApplication.getAppComponent().inject(this);
    }

    public void addTaskProcedure() {

        new Request().execute();

        KLog.e("New Task", "111");
    }

    private class Request extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            JustToDoStructureTable task = new JustToDoStructureTable(2, 1, "title1", "content1"
            ,"date1", "category1", "user1");
            justToDoDao.insert(task);
            KLog.e("New Task", task.task_title);

            return true;
        }
    }
}