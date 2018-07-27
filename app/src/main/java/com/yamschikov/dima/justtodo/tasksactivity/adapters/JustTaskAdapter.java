package com.yamschikov.dima.justtodo.tasksactivity.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JustTaskAdapter extends RecyclerView.Adapter<JustTaskAdapter.JustTaskHolder> {

    List<JustToDoStructureTable> justToDoStructureTablesList;

    public JustTaskAdapter(List<JustToDoStructureTable> justToDoStructureTablesList) {
        this.justToDoStructureTablesList = justToDoStructureTablesList;
    }

    @NonNull
    @Override
    public JustTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item3, parent, false);

        return new JustTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JustTaskHolder holder, int position) {

        JustToDoStructureTable justToDoStructureTable = justToDoStructureTablesList.get(position);

        holder.mJustTask.setText(justToDoStructureTable.task_title);
        holder.mNoteTask.setText(justToDoStructureTable.task_content);
        holder.mDateTask.setText(justToDoStructureTable.task_date);
    }

    @Override
    public int getItemCount() {
        return justToDoStructureTablesList.size();
    }

    public void removeItem(int position) {
        justToDoStructureTablesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(JustToDoStructureTable item, int position) {
        justToDoStructureTablesList.add(position, item);
        notifyItemInserted(position);
    }

    public void setData(List<JustToDoStructureTable> newData) {
        this.justToDoStructureTablesList = newData;
        notifyDataSetChanged();
    }

    public class JustTaskHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.justTask)
        TextView mJustTask;
        @BindView(R.id.noteTask)
        TextView mNoteTask;
        @BindView(R.id.dateTask)
        TextView mDateTask;

        @BindView(R.id.view_foreground)
        ConstraintLayout viewForeground;

        public JustTaskHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}