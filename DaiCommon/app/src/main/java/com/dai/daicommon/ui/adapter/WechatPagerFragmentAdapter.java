package com.dai.daicommon.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dai.daicommon.ui.frament.second.FirstPagerFragment;
import com.dai.daicommon.ui.frament.second.OtherPagerFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class WechatPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    public WechatPagerFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FirstPagerFragment.newInstance();
        } else {
            return OtherPagerFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
