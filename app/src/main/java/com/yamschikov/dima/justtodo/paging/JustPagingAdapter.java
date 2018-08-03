package com.yamschikov.dima.justtodo.paging;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JustPagingAdapter extends PagedListAdapter<JustToDoStructureTable, JustPagingAdapter.JustTaskPagingHolder>{

     public JustPagingAdapter(@NonNull DiffUtil.ItemCallback<JustToDoStructureTable> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public JustTaskPagingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item3, parent, false);
        JustTaskPagingHolder holder = new JustTaskPagingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JustTaskPagingHolder holder, int position) {

        JustToDoStructureTable justToDoStructureTable = getItem(position);

        holder.bindTo(justToDoStructureTable);
    }

    public static class JustTaskPagingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.justTask)
        TextView mJustTask;
        @BindView(R.id.noteTask)
        TextView mNoteTask;
        @BindView(R.id.dateTask)
        TextView mDateTask;

        @BindView(R.id.view_foreground)
        ConstraintLayout viewForeground;

        public JustTaskPagingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindTo(JustToDoStructureTable justToDoStructureTableList) {
            mJustTask.setText(justToDoStructureTableList.task_title);
            mNoteTask.setText(justToDoStructureTableList.task_title);
            mDateTask.setText(justToDoStructureTableList.task_title);
        }
    }

    public static final DiffUtil.ItemCallback<JustToDoStructureTable> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<JustToDoStructureTable>() {
                @Override
                public boolean areItemsTheSame(JustToDoStructureTable oldItem, JustToDoStructureTable newItem) {

                    KLog.e("areItemsTheSame----oldItem",  oldItem.task_title);
                        return oldItem.task_title == newItem.task_title;
                }

                @Override
                public boolean areContentsTheSame(JustToDoStructureTable oldItem, JustToDoStructureTable newItem) {
                    return oldItem.equals(newItem);
                }
            };
}