package com.yamschikov.dima.justtodo.addtask;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yamschikov.dima.justtodo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class AddTaskActivity extends AppCompatActivity implements AddTaskView{

    @BindView(R.id.toolbarAdd) Toolbar mToolbarAdd;
    @BindView(R.id.collapsingToolbarAdd) CollapsingToolbarLayout mCollapsingToolbarAdd;
    @BindView(R.id.textInputLayoutTitleTask) TextInputLayout mTextInputLayoutTitleTask;
    @BindView(R.id.textInputLayoutContentTask) TextInputLayout mTextInputLayoutContentTask;
    @BindView(R.id.btnSetDate) Button mBtnSetDate;
    @BindView(R.id.btnSetCategory) Button btnSetCategory;
    @BindView(R.id.btnAddTask) FloatingActionButton btnAddTask;

    AddTaskPresenter addTaskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarAdd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //collapsingToolbarLayout.setTitle("New Task");

        addTaskPresenter = new AddTaskPresenter();
        addTaskPresenter.attachView(this);
    }

    @OnClick(R.id.btnAddTask)
    public void btnaddtaskonclick(View view) {
        Toast.makeText(this, "You win!", LENGTH_SHORT).show();
        addTaskPresenter.addTaskProcedure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addtasksmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}