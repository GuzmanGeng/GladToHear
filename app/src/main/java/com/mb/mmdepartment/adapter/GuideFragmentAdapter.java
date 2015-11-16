package com.mb.mmdepartment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
public class GuideFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG=GuideFragmentAdapter.class.getSimpleName();
        private List<Fragment> lists;
    public GuideFragmentAdapter(FragmentManager fm,List<Fragment> lists) {
        super(fm);
        this.lists=lists;
    }
    @Override
    public Fragment getItem(int position) {
        return lists.get(position);
    }

    @Override
    public int getCount() {
        return lists.size();
    }
}
