package com.yamschikov.dima.justtodo.addtask;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.di.SharedPreferencesManager;
import com.yamschikov.dima.justtodo.prefsafe.PrefManager;
import com.yamschikov.dima.justtodo.room_db.JustToDoStructureTable;
import com.yamschikov.dima.justtodo.tasksactivity.BlankViewModel;
import com.yamschikov.dima.justtodo.tasksactivity.CustomDatePickerFragment;

import java.text.DateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTaskActivity extends AppCompatActivity implements CustomDatePickerFragment.OnDateSetListener {

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
    @BindView(R.id.currentDate)
    TextView mCurrentDate;

    //AddTaskPresenter addTaskPresenter;
    //PrefManager prefManager;

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    Snackbar snackbar;
    View snackbarView;

    BlankViewModel blankViewModel;

    @BindView(R.id.inputTaskTitle)
    EditText inputTaskTitle;

    @BindView(R.id.inputTaskContent)
    EditText inputTaskContent;

    String mTitle;
    String mContent;
    String mCategory;
    String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbarAdd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mCollapsingToolbarAdd.setTitle("ssssssssssssss");

        //addTaskPresenter = new AddTaskPresenter(getApplication());
        //addTaskPresenter.attachView(this);

        BaseApplication.getAppComponent().inject(this);

        //prefManager = new PrefManager(this);

        String currentDateTimeString = DateFormat.getDateInstance(DateFormat.FULL).format(new Date());
        mCurrentDate.setText(currentDateTimeString);

        blankViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);

        inputTaskTitle.addTextChangedListener(new TaskWatcher(inputTaskTitle));

        sharedPreferencesManager.setCheckedCategory("");
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
                                //prefManager.setCheckedCategory(filter_items[0]);
                                sharedPreferencesManager.setCheckedCategory(filter_items[0]);
                                break;
                            case 1:
                                btnLabel(1, filter_items[1]);
                                //prefManager.setCheckedCategory(filter_items[1]);
                                sharedPreferencesManager.setCheckedCategory(filter_items[1]);
                                break;
                            case 2:
                                btnLabel(2, filter_items[2]);
                                //prefManager.setCheckedCategory(filter_items[2]);
                                sharedPreferencesManager.setCheckedCategory(filter_items[2]);
                                break;
                            case 3:
                                btnLabel(3, filter_items[3]);
                                //prefManager.setCheckedCategory(filter_items[3]);
                                sharedPreferencesManager.setCheckedCategory(filter_items[3]);
                                break;
                            case 4:
                                btnLabel(4, filter_items[4]);
                                //prefManager.setCheckedCategory(filter_items[4]);
                                sharedPreferencesManager.setCheckedCategory(filter_items[4]);
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

                inputTaskTitle.onEditorAction(EditorInfo.IME_ACTION_DONE);
                inputTaskContent.onEditorAction(EditorInfo.IME_ACTION_DONE);

                submitTask();

                /*String mTitle = mTextInputLayoutTitleTask.getEditText().getText().toString();
                String mContent = mTextInputLayoutContentTask.getEditText().getText().toString();

                String mCategory;
                mCategory = prefManager.getCheckedCategory();
                KLog.e("mCategory", mCategory);

                String mDate;
                mDate = prefManager.getCheckedDate();

                if (mTitle.equals("") || mContent.equals("") || mTitle.equals("") && mContent.equals("")) {

                    btnAddTask.setEnabled(true);
                    btnAddTask.setImageResource(R.drawable.ic_block_addtask_white_24dp);

                    snackbar = Snackbar.make(btnAddTask, getResources().getString(R.string.snackbartaskisempty),
                            Snackbar.LENGTH_SHORT);
                    snackbarView = snackbar.getView();
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.RED);
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                } else {

                    btnAddTask.setEnabled(false);
                    btnAddTask.setImageResource(R.drawable.ic_done_white_24dp);
                    //addTaskPresenter.addTaskProcedure(mTitle, mContent);

                    //roomInsert
                    JustToDoStructureTable justToDoStructureTable = new JustToDoStructureTable();
                    justToDoStructureTable.task_title = mTitle;
                    justToDoStructureTable.task_content = mContent;
                    justToDoStructureTable.task_category = mCategory;
                    justToDoStructureTable.task_date = mDate;

                    blankViewModel.insert(justToDoStructureTable);

                    //snackbar message
                    snackbar = Snackbar.make(btnAddTask, getResources().getString(R.string.snackbattaskwelldone), Snackbar.LENGTH_SHORT);
                    snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                    mTextInputLayoutTitleTask.refreshDrawableState();
                    mTextInputLayoutTitleTask.getEditText().setText("");

                    mTextInputLayoutContentTask.refreshDrawableState();
                    mTextInputLayoutContentTask.getEditText().setText("");
                }*/
                break;

            case R.id.btnSetCategory:
                menuCategory();
                break;

            case R.id.btnSetDate:
                menuDate();
                break;

        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addtasksmenu, menu);
        return true;
    }*/

   /* @Override
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
    }*/

    //Alert Date
    private void menuDate() {

        CustomDatePickerFragment customDatePickerFragment = new CustomDatePickerFragment();
        customDatePickerFragment.show(getSupportFragmentManager(), "DatePicker");
        customDatePickerFragment.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        int month = monthOfYear + 1;
        String selectedDateM = "" + month;
        String selectedDateD = "" + dayOfMonth;
        if (month < 10) {
            selectedDateM = "0" + month;
        }
        if (dayOfMonth < 10) {
            selectedDateD = "0" + dayOfMonth;
        }

        String yearstr = String.valueOf(year).substring(2);

        String dd = yearstr.substring(2);

        String dateParam = yearstr + "/" + selectedDateM + selectedDateD;
        //prefManager.setCheckedDate(dateParam);

        String dateParamBtn = selectedDateD + "." + selectedDateM + "." + yearstr;
        KLog.e("date------->", "" + dateParam, "year"+yearstr);
        mBtnSetDate.setText(dateParamBtn);
        //prefManager.setCheckedDate(dateParamBtn);
        sharedPreferencesManager.setCheckedDate(dateParamBtn);

    }

    //Validate Task
    private boolean validateTask() {

        mTitle = mTextInputLayoutTitleTask.getEditText().getText().toString();
        mContent = mTextInputLayoutContentTask.getEditText().getText().toString();

        /*mCategory = prefManager.getCheckedCategory();
        mDate = prefManager.getCheckedDate();*/

        mCategory = sharedPreferencesManager.getCheckedCategory();
        mDate = sharedPreferencesManager.getCheckedDate();

        if (inputTaskTitle.getText().toString().trim().isEmpty()) {
            mTextInputLayoutTitleTask.setError(getString(R.string.snackbartaskisempty));
            //lock
            btnAddTask.setImageResource(R.drawable.ic_block_addtask_white_24dp);

            snackbar = Snackbar.make(btnAddTask, getResources().getString(R.string.snackbartaskisempty),
                    Snackbar.LENGTH_SHORT);
            snackbarView = snackbar.getView();
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.RED);
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
            return false;
        } else {

            mTextInputLayoutTitleTask.setErrorEnabled(false);
            //unlock
            btnAddTask.setImageResource(R.drawable.ic_done_white_24dp);
        }

        return true;
    }

    ///////////////
    private class TaskWatcher implements TextWatcher {

        private View view;

        private TaskWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.inputTaskTitle:
                    validateTask();
                    break;
            }
        }
    }

    private void submitTask() {
        if (!validateTask()) {
            return;
        }

        if (sharedPreferencesManager.getCheckedDate().equals("")) {

            String currentDateTimeString = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
            mDate = currentDateTimeString;
        }

        //roomInsert
        JustToDoStructureTable justToDoStructureTable = new JustToDoStructureTable();
        justToDoStructureTable.task_title = mTitle;
        justToDoStructureTable.task_content = mContent;
        justToDoStructureTable.task_category = mCategory;
        justToDoStructureTable.task_date = mDate;
        justToDoStructureTable.task_user_id = sharedPreferencesManager.getPrefUserId();

        blankViewModel.insert(justToDoStructureTable);

        //snackbar message
        snackbar = Snackbar.make(btnAddTask, getResources().getString(R.string.snackbattaskwelldone), Snackbar.LENGTH_SHORT);
        snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
        finish();
        sharedPreferencesManager.setCheckedDate("");
    }
}