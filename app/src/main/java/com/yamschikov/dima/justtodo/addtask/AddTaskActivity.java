package com.yamschikov.dima.justtodo.addtask;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.prefsafe.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;

public class AddTaskActivity extends AppCompatActivity implements AddTaskView {

    @BindView(R.id.toolbarAdd)
    Toolbar mToolbarAdd;
    @BindView(R.id.collapsingToolbarAdd)
    CollapsingToolbarLayout mCollapsingToolbarAdd;
    @BindView(R.id.textInputLayoutTitleTask)
    TextInputLayout mTextInputLayoutTitleTask;
    @BindView(R.id.textInputLayoutContentTask)
    TextInputLayout mTextInputLayoutContentTask;
    @BindView(R.id.btnSetDate)
    Button mBtnSetDate;
    @BindView(R.id.btnSetCategory)
    Button btnSetCategory;
    @BindView(R.id.btnAddTask)
    FloatingActionButton btnAddTask;

    AddTaskPresenter addTaskPresenter;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarAdd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //collapsingToolbarLayout.setTitle("New Task");

        addTaskPresenter = new AddTaskPresenter(getApplication());
        addTaskPresenter.attachView(this);

        prefManager = new PrefManager(this);
    }

    public void menuCategory() {

        final String[] filter_items = getResources().getStringArray(R.array.category_items);

        new AlertDialog.Builder(this)
                .setSingleChoiceItems(filter_items, 0, null)
                .setPositiveButton(R.string.action_filter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();

                        switch (selectedPosition) {
                            case 0:
                                btnLabel(0, filter_items[0]);
                                prefManager.setCheckedCategory(filter_items[0]);
                                break;
                            case 1:
                                btnLabel(1, filter_items[1]);
                                prefManager.setCheckedCategory(filter_items[1]);
                                break;
                            case 2:
                                btnLabel(2, filter_items[2]);
                                prefManager.setCheckedCategory(filter_items[2]);
                                break;
                            case 3:
                                btnLabel(3, filter_items[3]);
                                prefManager.setCheckedCategory(filter_items[3]);
                                break;
                            case 4:
                                btnLabel(4, filter_items[4]);
                                prefManager.setCheckedCategory(filter_items[4]);
                                break;
                        }
                    }
                })
                .show();
    }

    private String btnLabel(int index, String chackedLabel) {

        String returnCategory = "";

        switch (index) {

            case 0:
                returnCategory = chackedLabel;
                btnSetCategory.setText(returnCategory);
                break;
            case 1:
                returnCategory = chackedLabel;
                btnSetCategory.setText(returnCategory);
                break;
            case 2:
                returnCategory = chackedLabel;
                btnSetCategory.setText(returnCategory);
                break;
            case 3:
                returnCategory = chackedLabel;
                btnSetCategory.setText(returnCategory);
                break;
            case 4:
                returnCategory = chackedLabel;
                btnSetCategory.setText(returnCategory);
                break;

        }
        KLog.e("menuCategory__listSortAll", "--------->" + returnCategory);
        return returnCategory;
    }

    @OnClick({R.id.btnAddTask, R.id.btnSetCategory, R.id.btnSetDate})
    public void btnaddtaskonclick(View view) {


        switch (view.getId()) {

            case R.id.btnAddTask:
                Toast.makeText(this, "You win!", LENGTH_SHORT).show();

                String mTitle = mTextInputLayoutTitleTask.getEditText().getText().toString();
                String mContent = mTextInputLayoutContentTask.getEditText().getText().toString();
                String fgfg = btnSetCategory.getText().toString();
                //KLog.e("aaaaaaaaaaaaaa", fgfg);

                addTaskPresenter.addTaskProcedure(mTitle, mContent);
                //finish();
                break;

            case R.id.btnSetCategory:
                menuCategory();
                break;

        }
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