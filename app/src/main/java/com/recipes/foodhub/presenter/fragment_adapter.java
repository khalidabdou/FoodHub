package com.recipes.foodhub.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.recipes.foodhub.views.tab_new;
import com.recipes.foodhub.views.tab_ranking;

public class fragment_adapter extends FragmentStatePagerAdapter {

    int mapage;
    public fragment_adapter(FragmentManager fm,int numberPage){
        super(fm);
        this.mapage=numberPage;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                tab_new tab_new=new tab_new();
                return tab_new;
            case 1:
                tab_ranking tab_ranking=new tab_ranking();
                return tab_ranking;
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return mapage;
    }
}
