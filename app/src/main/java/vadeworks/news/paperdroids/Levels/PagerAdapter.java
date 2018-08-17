package vadeworks.news.paperdroids.Levels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vadeworks.news.paperdroids.News;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumofTabs;
    String mContent;
    public PagerAdapter(FragmentManager fm,int numofTabs,String content) {
        super(fm);
        mNumofTabs = numofTabs;
        mContent = content;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Tab1 tab1 = Tab1.newInstance(mContent);
                return tab1;
            case 1:
                Tab2 tab2 = Tab2.newInstance(mContent);
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumofTabs;
    }
}
