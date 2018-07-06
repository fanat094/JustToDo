package com.yamschikov.dima.justtodo.tasksactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import java.util.List;

public class FragmentTaskHome extends Fragment {

    private BlankViewModel mViewModel;
    LiveData<List<JustToDoStructureTable>> data;

    public static FragmentTaskHome newInstance() {
        return new FragmentTaskHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        data = mViewModel.getMedia();

        data.observe(this, new Observer<List<JustToDoStructureTable>>() {
            @Override
            public void onChanged(@Nullable List<JustToDoStructureTable> justToDoStructureTables) {
                String pppp = justToDoStructureTables.get(2).task_title;
                KLog.e("pppp", pppp);
            }
        });

        return inflater.inflate(R.layout.fragment_task_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        // TODO: Use the ViewModel
    }
}