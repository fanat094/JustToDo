package com.yamschikov.dima.justtodo.tasksactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.BaseApplication;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.addtask.AddTaskActivity;
import com.yamschikov.dima.justtodo.di.SharedPreferencesManager;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TasksActivity extends AppCompatActivity {

    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    @BindView(R.id.toolbarTask) Toolbar mToolbarTask;
    @BindView(R.id.fabTask) FloatingActionButton mFabTask;
    @BindView(R.id.drawerTask) DrawerLayout mDrawerTask;
    @BindView(R.id.navViewTask) NavigationView mNavViewTask;

    public static final String EXTRA_FIREBASEUSERNAME = "firebaseusername";
    public static final String EXTRA_FIREBASEUSERPIC = "firebaseuserpic";
    public static final String EXTRA_FIREBASEUSERLABEL = "firebaseuserlabel";

    public static final int SIGN_OUT_CODE_EMPTY = 1000;
    public static final int SIGN_OUT_CODE_FACEBOOK = 1001;
    public static final int SIGN_OUT_CODE_GOOGLE = 1002;

    public static final int EXTRA_FIREBASEUSERLABELONE = 1;
    public static final int EXTRA_FIREBASEUSERLABELTWOO = 2;

    TextView mIdUserName;
    CircleImageView mIdUserPic;
    NavController navController;
    View headerView;

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
        mIdUserPic = (CircleImageView) headerView.findViewById(R.id.idUserPic);

        userInfo();

        navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);
        NavigationUI.setupWithNavController(mNavViewTask, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawerTask);

        navController.addOnNavigatedListener(new NavController.OnNavigatedListener() {
            @Override
            public void onNavigated(@NonNull NavController controller, @NonNull NavDestination destination) {

                switch (destination.getId()) {

                    case R.id.navigationSignOut:

                        KLog.e("destinationwelcomeActivity");
                        sharedPreferencesManager.setFirstTimeLaunch(true);

                        int codecheck = sharedPreferencesManager.getCheckSignOut();

                        sharedPreferencesManager.setCheckSignOut(codecheck);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerTask.isDrawerOpen(GravityCompat.START)) {
            mDrawerTask.closeDrawer(GravityCompat.START);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void userInfo() {

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            int intlabel = bd.getInt(EXTRA_FIREBASEUSERLABEL);

            if (intlabel == EXTRA_FIREBASEUSERLABELONE) {

                KLog.e("IntLanel", intlabel);
                String getName = (String) bd.get(EXTRA_FIREBASEUSERNAME);
                mIdUserName.setText(getName);
                String getPic = (String) bd.get(EXTRA_FIREBASEUSERPIC);
                Glide.with(this)
                        .load(getPic)
                        .into(mIdUserPic);
            }

            if (intlabel == EXTRA_FIREBASEUSERLABELTWOO) {
                KLog.e("IntLanel", intlabel);
                mIdUserName.setText(getResources().getString(R.string.empty_just_to_do_user));
                Glide.with(this)
                        .load(getResources().getDrawable(R.drawable.book))
                        .into(mIdUserPic);
            } else {

                if (sharedPreferencesManager.getCheckSignOut() == SIGN_OUT_CODE_EMPTY) {

                    KLog.e("NO Null_SIGN_OUT_CODE_EMPTY");
                    mIdUserName.setText(sharedPreferencesManager.getFirstUserName());
                    Glide.with(this)
                            .load(getResources().getDrawable(R.drawable.book))
                            .into(mIdUserPic);
                } else {

                    KLog.e("NO Null");
                    mIdUserName.setText(sharedPreferencesManager.getFirstUserName());
                    Glide.with(this)
                            .load(sharedPreferencesManager.getFirstUserPic())
                            .into(mIdUserPic);
                }
            }
        }
    }
}