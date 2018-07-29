package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Adapter.ResultPagerAdapter;
import com.example.hyo_jin.themostcleversubway.FragmentReplacable;
import com.example.hyo_jin.themostcleversubway.Fragments.ResultFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.SubSearch1Fragment;
import com.example.hyo_jin.themostcleversubway.R;

public class ResultActivity extends AppCompatActivity {

    private final String TAG = "ResultActivity";

    private ViewPager mViewPager;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);
        init();

        // 인텐트 전달받기
        intent = getIntent();

        //Toast.makeText(this, intent.getStringExtra("station_arr"), Toast.LENGTH_SHORT).show();
        String depart = intent.getStringExtra("station_dep");
        String arrive = intent.getStringExtra("station_arr");

        Fragment fragment = new ResultFragment(depart, arrive);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.result_fragment, fragment);
        transaction.commit();

        setupViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViewPager(ViewPager viewPager) {
        ResultPagerAdapter adapter = new ResultPagerAdapter(getSupportFragmentManager(), new ResultFragment());

//        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void init() {
        mViewPager = (ViewPager) findViewById(R.id.container);
    }
}

