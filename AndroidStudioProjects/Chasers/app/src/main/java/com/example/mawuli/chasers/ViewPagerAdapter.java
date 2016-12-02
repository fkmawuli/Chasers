package com.example.mawuli.chasers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);

        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                FlagFragment flagFragment = new FlagFragment();
                return flagFragment;
            case 2:
                NotificationFragment notificationFragment = new NotificationFragment();
                return notificationFragment;
            case 3:
                ProfileFragment profileFragment = new ProfileFragment();
                        return profileFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
