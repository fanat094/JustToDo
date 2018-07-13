package com.yamschikov.dima.justtodo.tasksactivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTaskToday extends Fragment {

    @Inject
    JustToDoDao justToDoDao;


    public FragmentTaskToday() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        BaseApplication.getAppComponent().inject(this);

        new RequestRt().execute();

        return inflater.inflate(R.layout.fragment_fragment_task_today, container, false);
    }

    private class RequestRt extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            List<JustToDoStructureTable> employeesLiveData = justToDoDao.getAll();
//            String ggg = employeesLiveData.get(0).task_title;
 //           KLog.e("dffffffff", ggg);

            return true;
        }
    }
}