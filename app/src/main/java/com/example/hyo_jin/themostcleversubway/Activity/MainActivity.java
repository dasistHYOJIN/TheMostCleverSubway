package com.example.hyo_jin.themostcleversubway.Activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hyo_jin.themostcleversubway.Adapter.SectionsPagerAdapter;
import com.example.hyo_jin.themostcleversubway.Fragments.FavoriteFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.HistoryFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.ResultFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.SearchFragment;
import com.example.hyo_jin.themostcleversubway.Fragments.SubSearch2Fragment;
import com.example.hyo_jin.themostcleversubway.Fragments.SubSearch3Fragment;
import com.example.hyo_jin.themostcleversubway.Fragments.TestFragment;
import com.example.hyo_jin.themostcleversubway.MyFirebaseInstanceIDService;
import com.example.hyo_jin.themostcleversubway.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements SubSearch2Fragment.OnStationSelectedListener, SubSearch3Fragment.OnStationSelectedListener {
    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    SearchFragment searchFragment = new SearchFragment();

    // Tab Titles
    private String[] tabs = {"즐겨찾기", "검색", "검색기록", "테스트용"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity", "OnCreate: Starting ");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getToken();
        Log.v(TAG, FirebaseInstanceId.getInstance().getToken());
    }

    // Receive datas from SubSearchFragments
    // and Deliver to SubFragment
    public void onStationSelected(String station) {

        Log.v(TAG, station);

        if (searchFragment != null) {
            Log.v(TAG, "searchFragment는 null이 아니야");
            searchFragment.onStationClick(station);
        } else {
            Log.v(TAG, "searchFragment는 null이야");
            searchFragment = new SearchFragment();
            Bundle bundle = new Bundle();
            bundle.putString("station_name", station);
            searchFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_search, new SearchFragment()); // R.id.fragment_search를 다른걸로 바꿔야돼

            // Commit the transaction
            transaction.commit();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FavoriteFragment(), tabs[0]);
        adapter.addFragment(searchFragment, tabs[1]);
        adapter.addFragment(new HistoryFragment(), tabs[2]);
        //adapter.addFragment(new TestFragment(), tabs[3]);

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
