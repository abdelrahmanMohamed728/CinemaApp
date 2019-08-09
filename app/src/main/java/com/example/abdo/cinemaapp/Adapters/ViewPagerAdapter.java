package com.example.abdo.cinemaapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.abdo.cinemaapp.HomeFragment;
import com.example.abdo.cinemaapp.ProfileFragment;
import com.example.abdo.cinemaapp.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i==0)
            return new HomeFragment();
        else if(i==1)
            return new SearchFragment();
        else if (i==2)
            return new ProfileFragment();
        else
            return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
