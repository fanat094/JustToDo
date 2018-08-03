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
    public void deleteTask(JustToDoStructureTable justToDoStructureTable) {
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

    ///////////////////
    //delete all tasks
    public void deleteAll(String userId) {
        new deleteAllTasksAsyncTask(justToDoDao).execute(userId);
    }

    private static class deleteAllTasksAsyncTask extends AsyncTask<String, Void, Void> {
        private JustToDoDao mAsyncTaskDao;

        deleteAllTasksAsyncTask(JustToDoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.deleteAll(strings[0]);
            return null;
        }
    }

    //deleta tasks by categoty
    public void deleteTaskByCategory(String userId, String taskCategory) {
        new deleteTasksByCategory(justToDoDao).execute(userId, taskCategory);
    }

    private static class deleteTasksByCategory extends AsyncTask<String, Void, Void> {
        private JustToDoDao mAsyncTaskDao;

        deleteTasksByCategory(JustToDoDao dao) {
            mAsyncTaskDao = dao;

        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.deleteAllTaskByCategory(strings[0],strings[1]);
            return null;
        }
    }

    //deleta tasks by today
    public void deleteTaskByToday(String userId, String taskDate) {
        new deleteTaskByToday(justToDoDao).execute(userId, taskDate);
    }

    private static class deleteTaskByToday extends AsyncTask<String, Void, Void> {
        private JustToDoDao mAsyncTaskDao;

        deleteTaskByToday(JustToDoDao dao) {
            mAsyncTaskDao = dao;

        }

        @Override
        protected Void doInBackground(String... strings) {
            mAsyncTaskDao.deleteAllTaskByToday(strings[0],strings[1]);
            return null;
        }
    }
}