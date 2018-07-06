package com.yamschikov.dima.justtodo.addtask;

import android.content.Context;
import android.os.AsyncTask;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.prefsafe.PrefManager;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import javax.inject.Inject;

public class AddTaskPresenter {

    AddTaskView view;

    @Inject
    JustToDoDao justToDoDao;
    PrefManager prefManager;

    public void attachView(AddTaskView view) {
        this.view = view;
    }

    public AddTaskPresenter(Context context) {

        BaseApplication.getAppComponent().inject(this);
        prefManager = new PrefManager(context);
    }

    public void addTaskProcedure(String mTitle, String mContent) {

        //new RequestInsert().execute();

        KLog.e("New Task", "111");

        String mCategory;
        mCategory = prefManager.getCheckedCategory();
        KLog.e("mCategory", mCategory);

        MyTaskParams params = new MyTaskParams(mTitle, mContent,mCategory);
        RequestParams myTask = new RequestParams();
        myTask.execute(params);
    }

    private class RequestParams extends AsyncTask<MyTaskParams, Void, Boolean> {

        @Override
        protected Boolean doInBackground(MyTaskParams... myTaskParams) {

            JustToDoStructureTable task = new JustToDoStructureTable();

            task.task_title = myTaskParams[0].titletaskparam;
            task.task_content = myTaskParams[0].contenttaskparam;
            task.task_category = myTaskParams[0].categorytaskparam;
            task.task_date = "task_date";
            task.task_user_id = "task_user_id";

            justToDoDao.insert(task);
            KLog.e("New Task Param", task.task_title);
            KLog.e("New Task Param Content", task.task_content);
            KLog.e("New Task Param Category", task.task_category);

            return true;
        }
    }

    //pre req
    private class RequestInsert extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            JustToDoStructureTable task = new JustToDoStructureTable();

            task.task_title = "task33333333333333";
            task.task_category = "task3";
            task.task_content = "task3";
            task.task_date = "task3";
            task.task_user_id = "task3";

            justToDoDao.insert(task);
            KLog.e("New Task", task.task_title);

            return true;
        }
    }
}