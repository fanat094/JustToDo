package com.yamschikov.dima.justtodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yamschikov.dima.justtodo.tasksactivity.TasksActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static android.widget.Toast.LENGTH_SHORT;

public class WelcomeActivity extends AppCompatActivity {

    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;

    @BindView(R.id.view_pager_welcome)
    ViewPager mViewPageWelcome;
    @BindView(R.id.btnSignEmpty)
    Button mBtnSignEmpty;

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
    }

    @OnClick({R.id.btnSignGoogle, R.id.btnSignFacebook, R.id.btnSignEmpty })
    public void ff(View view) {

        switch (view.getId()){

            case R.id.btnSignEmpty:
                Intent welcomeintent = new Intent(WelcomeActivity.this, TasksActivity.class);
                startActivity(welcomeintent);
                finish();
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
}