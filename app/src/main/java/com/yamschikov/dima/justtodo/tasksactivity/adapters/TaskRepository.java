package com.yamschikov.dima.justtodo.tasksactivity.adapters;

import android.os.AsyncTask;

import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import javax.inject.Inject;

public class TaskRepository {

    @Inject
    JustToDoDao justToDoDao;

    public TaskRepository() {

        BaseApplication.getAppComponent().inject(this);
    }

    //delete task
    public void deleteTask(JustToDoStructureTable justToDoStructureTable)  {
        new deleteTaskAsyncTask(justToDoDao).execute(justToDoStructureTable);
    }

    private static class deleteTaskAsyncTask extends AsyncTask<JustToDoStructureTable, Void, Void> {
        private JustToDoDao mAsyncTaskDao;

        public deleteTaskAsyncTask(JustToDoDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JustToDoStructureTable... params) {

            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    //insert Task
    public void insert(JustToDoStructureTable justToDoStructureTable) {
        new insertAsyncTask(justToDoDao).execute(justToDoStructureTable);
    }

    //insert task
    private static class insertAsyncTask extends AsyncTask<JustToDoStructureTable, Void, Void> {

        private JustToDoDao mAsyncTaskDao;

        insertAsyncTask(JustToDoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final JustToDoStructureTable... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}