package com.cxz.headline.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentLists;
    private List<String> mTitleLists;

    public BasePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
        super(fm);
        this.mFragmentLists = fragmentList;
        this.mTitleLists = new ArrayList<>(Arrays.asList(titles));
    }

    public BasePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.mFragmentLists = fragmentList;
        this.mTitleLists = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return mTitleLists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleLists.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void recreateItems(List<Fragment> fragmentList, List<String> titleList) {
        this.mFragmentLists = fragmentList;
        this.mTitleLists = titleList;
        notifyDataSetChanged();
    }
}
