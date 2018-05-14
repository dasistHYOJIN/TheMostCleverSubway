package com.example.hyo_jin.themostcleversubway.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hyo_jin.themostcleversubway.Adapter.ResultPagerAdapter;
import com.example.hyo_jin.themostcleversubway.Adapter.SectionsPagerAdapter;
import com.example.hyo_jin.themostcleversubway.Fragments.FavoriteFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.HistoryFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.ResultFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.SearchFragment;
import com.example.hyo_jin.themostcleversubway.R;

public class ResultActivity extends AppCompatActivity {

    private ResultPagerAdapter mResultPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResultPagerAdapter = new ResultPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        Toast.makeText(this, "Testing result activity ", Toast.LENGTH_SHORT).show();


        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void setupViewPager(ViewPager viewPager) {
        ResultPagerAdapter adapter = new ResultPagerAdapter(getSupportFragmentManager(), new ResultFragment());

//        viewPager.setAdapter(adapter);
    }

}
