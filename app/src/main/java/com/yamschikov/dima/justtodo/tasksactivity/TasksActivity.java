package com.yamschikov.dima.justtodo.tasksactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.addtask.AddTaskActivity;
import com.yamschikov.dima.justtodo.di.SharedPreferencesManager;
import com.yamschikov.dima.justtodo.prefsafe.PrefManager;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class TasksActivity extends AppCompatActivity {

    TextView mIdUserName;
    TextView mIdUserEmail;
    CircleImageView mIdUserPic;

    @BindView(R.id.toolbarTask)
    Toolbar mToolbarTask;
    @BindView(R.id.fabTask)
    FloatingActionButton mFabTask;
    @BindView(R.id.drawerTask)
    DrawerLayout mDrawerTask;
    @BindView(R.id.navViewTask)
    NavigationView mNavViewTask;

    View headerView;
    //PrefManager prefManager;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    NavController navController;

    public static final String EXTRA_FIREBASEUSERNAME = "firebaseusername";
    public static final String EXTRA_FIREBASEUSEREMAIL = "firebaseuseremail";
    public static final String EXTRA_FIREBASEUSERPIC = "firebaseuserpic";
    public static final String EXTRA_FIREBASEUSERLABEL = "firebaseuserlabel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);
        BaseApplication.getAppComponent().inject(this);

        setSupportActionBar(mToolbarTask);

        mFabTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent welcomeintent = new Intent(TasksActivity.this, AddTaskActivity.class);
                startActivity(welcomeintent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerTask, mToolbarTask, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerTask.addDrawerListener(toggle);
        toggle.syncState();

        headerView = mNavViewTask.getHeaderView(0);
        mIdUserName = (TextView) headerView.findViewById(R.id.idUserName);
        mIdUserEmail = (TextView) headerView.findViewById(R.id.idUserEmail);
        mIdUserPic = (CircleImageView) headerView.findViewById(R.id.idUserPic);

        //prefManager = new PrefManager(this);

        userInfo();

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        NavigationUI.setupWithNavController(mNavViewTask, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawerTask);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerTask.isDrawerOpen(GravityCompat.START)) {
            mDrawerTask.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasks, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return NavigationUI.onNavDestinationSelected(item,
                Navigation.findNavController(this, R.id.my_nav_host_fragment)) ||
                super.onOptionsItemSelected(item);
    }

    private void userInfo() {

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            int intlabel = bd.getInt(EXTRA_FIREBASEUSERLABEL);

            if (intlabel == 1) {

                KLog.e("IntLanel", intlabel);
                String getName = (String) bd.get(EXTRA_FIREBASEUSERNAME);
                mIdUserName.setText(getName);
                String getEmail = (String) bd.get(EXTRA_FIREBASEUSEREMAIL);
                mIdUserEmail.setText(getEmail);
                String getPic = (String) bd.get(EXTRA_FIREBASEUSERPIC);
                Glide.with(this)
                        .load(getPic)
                        .into(mIdUserPic);
            } else {
                KLog.e("NO Null");
                mIdUserName.setText(sharedPreferencesManager.getFirstUserName());
                mIdUserEmail.setText(sharedPreferencesManager.getFirstUserEmail());
                Glide.with(this)
                        .load(sharedPreferencesManager.getFirstUserPic())
                        .into(mIdUserPic);
            }
        }
    }
}