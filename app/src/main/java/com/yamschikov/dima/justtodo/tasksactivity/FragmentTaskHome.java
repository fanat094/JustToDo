package com.yamschikov.dima.justtodo.tasksactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.RecyclerItemTouchHelper;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.TaskTodayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTaskHome extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private BlankViewModel mViewModel;
    LiveData<List<JustToDoStructureTable>> data;

    @BindView(R.id.task_list_rv)
    RecyclerView mTaskListRv;

    Snackbar snackbar;
    View snackbarView;

    private TaskTodayAdapter mTaskTodayAdapter;

    List<JustToDoStructureTable> justToDoStructureTablesList;

    List<JustToDoStructureTable> localList;

    public static FragmentTaskHome newInstance() {
        return new FragmentTaskHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_home, container, false);
        ButterKnife.bind(this, view);

        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        data = mViewModel.getAllTask();

        justToDoStructureTablesList = new ArrayList<>();
        localList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mTaskListRv.setItemAnimator(new DefaultItemAnimator());
        mTaskListRv.setLayoutManager(mLayoutManager);
        DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mTaskListRv.addItemDecoration(decor);

        data.observe(this, new Observer<List<JustToDoStructureTable>>() {
            @Override
            public void onChanged(@Nullable List<JustToDoStructureTable> justToDoStructureTables) {

                KLog.e("Check-------------------------!");

                justToDoStructureTablesList = justToDoStructureTables;
                mTaskTodayAdapter = new TaskTodayAdapter(justToDoStructureTablesList);
                mTaskListRv.setAdapter(mTaskTodayAdapter);
                mTaskTodayAdapter.setData(justToDoStructureTablesList);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        // TODO: Use the ViewModel
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mTaskListRv);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        /*if (viewHolder instanceof TaskTodayAdapter.TaskTodayHolder) {
            // remove the item from recycler view
            mTaskTodayAdapter.removeItem(viewHolder.getAdapterPosition());
        }*/

        if (viewHolder instanceof TaskTodayAdapter.TaskTodayHolder) {
            // get the removed item name to display it in snack bar
            String name = justToDoStructureTablesList.get(viewHolder.getAdapterPosition()).task_title;

            // backup of removed item for undo purpose
            final JustToDoStructureTable deletedItem = justToDoStructureTablesList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mTaskTodayAdapter.removeItem(viewHolder.getAdapterPosition());
            KLog.e("deletedIndex---", deletedItem);
            mViewModel.deleteTask(deletedItem);
            mTaskTodayAdapter.notifyDataSetChanged();

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
                    mTaskTodayAdapter.restoreItem(deletedItem, deletedIndex);

                    //roomInsertUndo
                    JustToDoStructureTable justToDoStructureTable = new JustToDoStructureTable();
                    justToDoStructureTable.task_title = justToDoStructureTablesList.get(deletedIndex).task_title;
                    justToDoStructureTable.task_content = justToDoStructureTablesList.get(deletedIndex).task_content;
                    justToDoStructureTable.task_category = justToDoStructureTablesList.get(deletedIndex).task_category;
                    justToDoStructureTable.task_date = justToDoStructureTablesList.get(deletedIndex).task_date;

                    mViewModel.insert(justToDoStructureTable);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}