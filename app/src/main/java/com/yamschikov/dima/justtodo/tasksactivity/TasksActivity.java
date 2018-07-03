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

import com.bumptech.glide.Glide;
import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.R;
import com.yamschikov.dima.justtodo.addtask.AddTaskActivity;
import com.yamschikov.dima.justtodo.prefsafe.PrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class TasksActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

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

        mNavViewTask.setNavigationItemSelectedListener(this);

        headerView = mNavViewTask.getHeaderView(0);
        mIdUserName = (TextView) headerView.findViewById(R.id.idUserName);
        mIdUserEmail = (TextView) headerView.findViewById(R.id.idUserEmail);
        mIdUserPic = (CircleImageView) headerView.findViewById(R.id.idUserPic);
        //mIdUserName.setText("Your Text Here");

        prefManager = new PrefManager(this);

        userr();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerTask.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userr() {

        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            int intlabel = bd.getInt("label");

            if (intlabel == 1) {

                KLog.e("IntLanel", intlabel);
                String getName = (String) bd.get("firebaseusername");
                mIdUserName.setText(getName);
                String getEmail = (String) bd.get("firebaseuseremail");
                mIdUserEmail.setText(getEmail);
                String getPic = (String) bd.get("firebaseuserpic");
                Glide.with(this)
                        .load(getPic)
                        .into(mIdUserPic);
            } else {
                KLog.e("NO Null");
                mIdUserName.setText(prefManager.getFirstUserName());
                mIdUserEmail.setText(prefManager.getFirstUserEmail());
                Glide.with(this)
                        .load(prefManager.getFirstUserPic())
                        .into(mIdUserPic);
            }
        }
    }
}