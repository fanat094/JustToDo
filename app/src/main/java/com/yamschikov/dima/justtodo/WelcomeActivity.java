package com.yamschikov.dima.justtodo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.faceboook_auth.FacebookView;
import com.yamschikov.dima.justtodo.faceboook_auth.FireBaseFacebookPresenter;
import com.yamschikov.dima.justtodo.prefsafe.PrefManager;
import com.yamschikov.dima.justtodo.tasksactivity.TasksActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WelcomeActivity extends AppCompatActivity implements FacebookView {

    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;

    @BindView(R.id.view_pager_welcome)
    ViewPager mViewPageWelcome;
    @BindView(R.id.btnSignEmpty)
    Button mBtnSignEmpty;
    private PrefManager prefManager;

    private CallbackManager mCallbackManager;
    private static final String TAG = "FacebookAuthentication";

    private FirebaseAuth mAuth;
    FireBaseFacebookPresenter fireBaseFacebookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mViewPageWelcome = (ViewPager) findViewById(R.id.view_pager_welcome);

        ButterKnife.bind(this);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2};

        myViewPagerAdapter = new MyViewPagerAdapter();
        mViewPageWelcome.setAdapter(myViewPagerAdapter);
        mViewPageWelcome.addOnPageChangeListener(viewPagerPageChangeListener);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            KLog.e("launchHomeScreen", prefManager.isFirstTimeLaunch());

        }
        else {

            KLog.e("launchHomeScreenFALSE");
            launchHomeScreen();
            finish();
        }

        //facebook
        mAuth = FirebaseAuth.getInstance();

        fireBaseFacebookPresenter = new FireBaseFacebookPresenter();
        fireBaseFacebookPresenter.attachView(this);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

    }

    @OnClick({R.id.btnSignGoogle, R.id.btnSignFacebook, R.id.btnSignEmpty})
    public void ff(View view) {

        switch (view.getId()) {

            case R.id.btnSignEmpty:
                Intent welcomeintent = new Intent(WelcomeActivity.this, TasksActivity.class);
                startActivity(welcomeintent);
                finish();
                //launchHomeScreen();

            case R.id.btnSignFacebook:
                prefManager.setFirstTimeLaunch(false);
                LoginManager.getInstance().logInWithReadPermissions(WelcomeActivity.this,
                        Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        fireBaseFacebookPresenter.handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });
                break;
        }
    }


    private int getItem(int i) {
        return mViewPageWelcome.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
            } else {
                // still pages are left
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, TasksActivity.class));
        finish();
    }

    //facebook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            // updateUser(currentUser);
        }
    }

    @Override
    public void onReadyActivityStartForResult(Intent intent, int i) {

        startActivityForResult(intent, i);

    }

    @Override
    public void updateUser(FirebaseUser user) {

        String mUserIdNameTextView = "";
        String mUserIdEmailTextView = "";
        String mUserIdPicTextView = "";

        KLog.e("useruser", user.getDisplayName());

        if (user != null) {
            mUserIdNameTextView = user.getDisplayName();
            mUserIdEmailTextView = user.getEmail();
            mUserIdPicTextView = String.valueOf(user.getPhotoUrl());

            Intent intent = new Intent(this, TasksActivity.class);
            intent.putExtra("firebaseusername", mUserIdNameTextView);
            intent.putExtra("firebaseuseremail", mUserIdEmailTextView);
            intent.putExtra("firebaseuserpic", mUserIdPicTextView);
            intent.putExtra("label", 1);
            startActivity(intent);
            finish();

            //prefUser
            prefManager.setFirstUser(mUserIdNameTextView, mUserIdEmailTextView, mUserIdPicTextView);
            KLog.e("refManager.setFirstUser", mUserIdEmailTextView);

            //launchHomeScreen();


        } /*else {
            /*mStatusTextView.setText("Signed Out");
            mDetailTextView.setText(null);
            mFacebookIconImageView.setImageDrawable(getResources().getDrawable(R.drawable.logo_standard));

            mFacebookBtn.setVisibility(View.VISIBLE);
            sign_out_and_disconnect.setVisibility(View.GONE);*/
    }

}