package com.yamschikov.dima.justtodo.tasksactivity;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.di.SharedPreferencesManager;
import com.yamschikov.dima.justtodo.room_db.JustToDoDao;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.JustTaskAdapter;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.RecyclerItemTouchHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTaskToday extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @Inject
    JustToDoDao justToDoDao;

    private BlankViewModel mTaskTodayViewModel;
    LiveData<List<JustToDoStructureTable>> mTaskTodayData;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @BindView(R.id.task_list_rv)
    RecyclerView mTaskListRv;

    @BindView(R.id.mEmptyView)
    TextView mEmptyView;

    @BindView(R.id.mEmptyPic)
    ImageView mEmptyPic;

    Snackbar snackbar;
    View snackbarView;

    private JustTaskAdapter mJustTaskAdapter;

    List<JustToDoStructureTable> justToDoStructureTablesList;


    public FragmentTaskToday() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_task_today, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        BaseApplication.getAppComponent().inject(this);

        mTaskTodayViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        mTaskTodayData = mTaskTodayViewModel.getTasksToday(getCurrentTaskDate()
                , sharedPreferencesManager.getPrefUserId());

        justToDoStructureTablesList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mTaskListRv.setItemAnimator(new DefaultItemAnimator());
        mTaskListRv.setLayoutManager(mLayoutManager);
        DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mTaskListRv.addItemDecoration(decor);

        mTaskTodayData.observe(this, new Observer<List<JustToDoStructureTable>>() {
            @Override
            public void onChanged(@Nullable List<JustToDoStructureTable> justToDoStructureTables) {

                if (justToDoStructureTables.size() != 0) {
                    KLog.e("todays", justToDoStructureTables.get(0).task_title);

                    justToDoStructureTablesList = justToDoStructureTables;
                    mJustTaskAdapter = new JustTaskAdapter(justToDoStructureTablesList);
                    mTaskListRv.setAdapter(mJustTaskAdapter);
                    mJustTaskAdapter.setData(justToDoStructureTablesList);

                    mTaskListRv.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                    mEmptyPic.setVisibility(View.GONE);
                } else {
                    KLog.e("todays NO");

                    mTaskListRv.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                    mEmptyPic.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = ViewModelProviders.of(this).get(FragmentTaskWorkViewModel.class);
        // TODO: Use the ViewModel
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mTaskListRv);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tasks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_delete_all:

                if (justToDoStructureTablesList.size() != 0) {

                    mJustTaskAdapter.removeAllItem();
                    mTaskTodayViewModel.deleteTaskByToday(getCurrentTaskDate()
                            , sharedPreferencesManager.getPrefUserId());

                } else {

                    mTaskListRv.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                    mEmptyPic.setVisibility(View.VISIBLE);

                    snackbar = Snackbar.make(mTaskListRv, getResources().getString(R.string.snackbartaskisempty),
                            Snackbar.LENGTH_SHORT);
                    snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getCurrentTaskDate() {
        String currentDateString = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
        return currentDateString;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof JustTaskAdapter.JustTaskHolder) {
            // get the removed item name to display it in snack bar
            String name = justToDoStructureTablesList.get(viewHolder.getAdapterPosition()).task_title;

            // backup of removed item for undo purpose
            final JustToDoStructureTable deletedItem = justToDoStructureTablesList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mJustTaskAdapter.removeItem(viewHolder.getAdapterPosition());
            KLog.e("deletedIndex---", deletedItem);
            mTaskTodayViewModel.deleteTask(deletedItem);
            mJustTaskAdapter.notifyDataSetChanged();

            // showing snack bar with Undo option
            snackbar = Snackbar.make(mTaskListRv, name + " " + getResources().getString(R.string.messageremovedfromtask),
                    Snackbar.LENGTH_LONG);

            snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mJustTaskAdapter.restoreItem(deletedItem, deletedIndex);

                    //roomInsertUndo
                    JustToDoStructureTable justToDoStructureTable = new JustToDoStructureTable();
                    justToDoStructureTable.task_title = justToDoStructureTablesList.get(deletedIndex).task_title;
                    justToDoStructureTable.task_content = justToDoStructureTablesList.get(deletedIndex).task_content;
                    justToDoStructureTable.task_category = justToDoStructureTablesList.get(deletedIndex).task_category;
                    justToDoStructureTable.task_date = justToDoStructureTablesList.get(deletedIndex).task_date;
                    justToDoStructureTable.task_user_id = justToDoStructureTablesList.get(deletedIndex).task_user_id;

                    mTaskTodayViewModel.insert(justToDoStructureTable);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}