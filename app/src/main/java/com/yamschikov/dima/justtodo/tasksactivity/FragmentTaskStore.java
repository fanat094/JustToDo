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
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.di.SharedPreferencesManager;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.JustTaskAdapter;
import com.yamschikov.dima.justtodo.tasksactivity.adapters.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTaskStore extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private BlankViewModel mTaskStoreViewModel;
    LiveData<List<JustToDoStructureTable>> mTaskStoreData;

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

    public static FragmentTaskHome newInstance() {
        return new FragmentTaskHome();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_home, container, false);
        ButterKnife.bind(this, view);

        BaseApplication.getAppComponent().inject(this);

        mTaskStoreViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        mTaskStoreData = mTaskStoreViewModel.getTasksStore(getResources().getString(R.string.store_task_fragment)
                , sharedPreferencesManager.getPrefUserId());

        justToDoStructureTablesList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mTaskListRv.setItemAnimator(new DefaultItemAnimator());
        mTaskListRv.setLayoutManager(mLayoutManager);
        DividerItemDecoration decor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mTaskListRv.addItemDecoration(decor);

        mTaskStoreData.observe(this, new Observer<List<JustToDoStructureTable>>() {
            @Override
            public void onChanged(@Nullable List<JustToDoStructureTable> justToDoStructureTables) {

                if (justToDoStructureTables.size() != 0) {

                    justToDoStructureTablesList = justToDoStructureTables;
                    mJustTaskAdapter = new JustTaskAdapter(justToDoStructureTablesList);
                    mTaskListRv.setAdapter(mJustTaskAdapter);
                    mJustTaskAdapter.setData(justToDoStructureTablesList);

                    mTaskListRv.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                    mEmptyPic.setVisibility(View.GONE);
                } else {

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
        // TODO: Use the ViewModel
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
                new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mTaskListRv);
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
            mTaskStoreViewModel.deleteTask(deletedItem);
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

                    mTaskStoreViewModel.insert(justToDoStructureTable);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}