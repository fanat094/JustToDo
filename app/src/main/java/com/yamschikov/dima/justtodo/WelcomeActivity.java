package com.yamschikov.dima.justtodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.socks.library.KLog;
import com.yamschikov.dima.justtodo.di.SharedPreferencesManager;
import com.yamschikov.dima.justtodo.faceboook_auth.FacebookView;
import com.yamschikov.dima.justtodo.faceboook_auth.FireBaseFacebookPresenter;
import com.yamschikov.dima.justtodo.google_auth.FireBaseGoogleSigInPresenter;
import com.yamschikov.dima.justtodo.google_auth.GoogleInView;
import com.yamschikov.dima.justtodo.tasksactivity.TasksActivity;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity implements FacebookView, GoogleInView
        , GoogleApiClient.OnConnectionFailedListener {

    private SliderPagerAdapter sliderPagerAdapter;
    private int[] layouts;

    @BindView(R.id.view_pager_welcome)
    ViewPager mViewPageWelcome;
    @BindView(R.id.btnSignEmpty)
    Button mBtnSignEmpty;

    @BindView(R.id.btnSignGoogle)
    Button btnSignGoogle;
    //private PrefManager prefManager;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private CallbackManager mCallbackManager;
    private static final String TAG = "FacebookAuthentication";
    private static final String TAGGOOGLE = "FacebookAuthentication";

    private static final String TAGListner = "TAGListner";

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    FireBaseFacebookPresenter fireBaseFacebookPresenter;
    FireBaseGoogleSigInPresenter fireBaseGoogleSigInPresenter;

    public static final String EXTRA_FIREBASEUSERNAME = "firebaseusername";
    public static final String EXTRA_FIREBASEUSEREMAIL = "firebaseuseremail";
    public static final String EXTRA_FIREBASEUSERPIC = "firebaseuserpic";
    public static final String EXTRA_FIREBASEUSERLABEL = "firebaseuserlabel";

    public static final int SIGN_OUT_CODE = 1001;
    public static final int SIGN_OUT_CODE_EMPTY = 1000;
    private static final int RC_SIGN_IN = 9001;
    public static final int SIGN_OUT_CODE_GOOGLE = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome4);

        ButterKnife.bind(this);
        BaseApplication.getAppComponent().inject(this);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        //Google
        fireBaseGoogleSigInPresenter = new FireBaseGoogleSigInPresenter();
        fireBaseGoogleSigInPresenter.attachView(this);

        buildGoogleApiClient();

        //facebook
        fireBaseFacebookPresenter = new FireBaseFacebookPresenter();
        fireBaseFacebookPresenter.attachView(this);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        KLog.e("MMMMMMMMM1", mGoogleApiClient);

        // Checking for first time launch - before calling setContentView()
        if (sharedPreferencesManager.isFirstTimeLaunch()) {
            KLog.e("launchHomeScreen", sharedPreferencesManager.isFirstTimeLaunch());

            switch (sharedPreferencesManager.getCheckSignOut()) {

                case SIGN_OUT_CODE:

                    KLog.e("CODE------>", sharedPreferencesManager.getCheckSignOut() + " " + SIGN_OUT_CODE);

                    fireBaseFacebookPresenter.facebookSignOut();
                    break;

                case SIGN_OUT_CODE_GOOGLE:

                    KLog.e("CODE------>SIGN_OUT_CODE_GOOGLE", sharedPreferencesManager.getCheckSignOut());
                    KLog.e("MMMMMMMMM2", mGoogleApiClient);

                    fireBaseGoogleSigInPresenter.signOut(mGoogleApiClient);

                    break;
                case SIGN_OUT_CODE_EMPTY:
                    KLog.e("CODE------>SIGN_OUT_CODE_EMPTY", sharedPreferencesManager.getCheckSignOut());
                    break;
            }

        } else {

            KLog.e("launchHomeScreenFALSE");
            launchHomeScreen();
            finish();
        }

        //view pager
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2};

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);


        sliderPagerAdapter = new SliderPagerAdapter();
        mViewPageWelcome.setAdapter(sliderPagerAdapter);
        indicator.setViewPager(mViewPageWelcome);
        mViewPageWelcome.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    public void buildGoogleApiClient() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.firebase_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mGoogleApiClient.connect();
    }

    @OnClick({R.id.btnSignGoogle, R.id.btnSignFacebook, R.id.btnSignEmpty})
    public void SignInButtonsOnClick(View view) {

        switch (view.getId()) {

            case R.id.btnSignEmpty:
                sharedPreferencesManager.setFirstTimeLaunch(false);
                Intent welcomeintent = new Intent(WelcomeActivity.this, TasksActivity.class);
                welcomeintent.putExtra(EXTRA_FIREBASEUSERLABEL, 2);
                startActivity(welcomeintent);

                sharedPreferencesManager.setFirstUser(getResources().getString(R.string.empty_just_to_do_user)
                        , getResources().getString(R.string.empty_user), "");

                sharedPreferencesManager.setCheckSignOut(SIGN_OUT_CODE_EMPTY);
                sharedPreferencesManager.setPrefUserId("empty");
                finish();
                //launchHomeScreen();
                break;

            case R.id.btnSignFacebook:

                sharedPreferencesManager.setFirstTimeLaunch(false);
                sharedPreferencesManager.setCheckSignOut(SIGN_OUT_CODE);
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

            case R.id.btnSignGoogle:

                sharedPreferencesManager.setFirstTimeLaunch(false);
                sharedPreferencesManager.setCheckSignOut(SIGN_OUT_CODE_GOOGLE);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                fireBaseGoogleSigInPresenter.signIn(signInIntent, mAuth, mGoogleApiClient);
                break;
        }
    }

    //Google Faoled
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        KLog.e(TAGGOOGLE, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Sign has error.", Toast.LENGTH_SHORT).show();
    }

    private void launchHomeScreen() {
        sharedPreferencesManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, TasksActivity.class));
        finish();
    }

    //facebook
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                fireBaseGoogleSigInPresenter.firebaseAuthWithGoogle(account);
            } else {
                //updateUser(null);
            }
        } else {

            mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
        String mUserIdTextView = "";

//        KLog.e("useruser", user.getDisplayName());

        if (user != null) {
            mUserIdNameTextView = user.getDisplayName();
            mUserIdEmailTextView = user.getEmail();
            mUserIdPicTextView = String.valueOf(user.getPhotoUrl());
            mUserIdTextView = user.getUid();

            sharedPreferencesManager.setPrefUserId(mUserIdTextView);
            KLog.e("updateUser--->mUserIdTextView", mUserIdTextView);

            Intent intent = new Intent(this, TasksActivity.class);
            intent.putExtra(EXTRA_FIREBASEUSERNAME, mUserIdNameTextView);
            intent.putExtra(EXTRA_FIREBASEUSEREMAIL, mUserIdEmailTextView);
            intent.putExtra(EXTRA_FIREBASEUSERPIC, mUserIdPicTextView);
            intent.putExtra(EXTRA_FIREBASEUSERLABEL, 1);
            startActivity(intent);
            finish();

            //prefUser
            sharedPreferencesManager.setFirstUser(mUserIdNameTextView, mUserIdEmailTextView, mUserIdPicTextView);
            KLog.e("refManager.setFirstUser", mUserIdEmailTextView);

            //launchHomeScreen();

        }

        /*KLog.d("Recordered in firebase");
        Toast.makeText(this, "Email logged",
                Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onBackPressed() {
        /*new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        WelcomeActivity.super.onBackPressed();
                    }
                }).create().show();*/
        finish();
    }

    //ViewPager
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

    //SliderPagerAdapter
    public class SliderPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public SliderPagerAdapter() {
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
}