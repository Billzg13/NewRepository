package com.example.billzg.recordingappfirsttry;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AppStartedActivity extends AppCompatActivity {
    private static final String TAG = "AppStartedActivity";

    private SectionPageAdapter mSectionPageAdapter ;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_started);
        Log.d(TAG, "onCreate: started");

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Log.d(TAG, "onCreate: end of onCreate");

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.AddFragment(new RecordingController(), "Record");
        adapter.AddFragment(new MediaPlayerController(), "Saved");
        viewPager.setAdapter(adapter);

    }

}
