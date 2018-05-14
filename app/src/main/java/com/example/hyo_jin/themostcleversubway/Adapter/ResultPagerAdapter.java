package com.example.hyo_jin.themostcleversubway.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyo-Jin on 2018-05-06.
 */

public class ResultPagerAdapter extends FragmentPagerAdapter {

    Fragment fragment;

    public ResultPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ResultPagerAdapter(FragmentManager fm, Fragment fragment) {
        super(fm);
        this.fragment = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}

/*

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        //return PlaceholderFragment.newInstance(position + 1);
        return mFragmentList.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
*/
