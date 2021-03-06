package vadeworks.news.paperdroids.Hindi.Bbc.tabs;

/*
  Created by ashwinchandlapur on 14/02/18.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPagerAdapter_BBC extends FragmentStatePagerAdapter {

    private final CharSequence[] Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    private final int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_BBC(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_BBC tab1 = new Tab1_Headlines_BBC();
            return tab1;
        } else if (position == 1) {
            Tab2_National_BBC tab2 = new Tab2_National_BBC();
            return tab2;
        } else if (position == 2) {
            Tab3_World_BBC tab3 = new Tab3_World_BBC();
            return tab3;
        } else if (position == 3) {
            Tab4_Sports_BBC tab4 = new Tab4_Sports_BBC();
            return tab4;
        } else {
            Tab5_Cinema_BBC tab5 = new Tab5_Cinema_BBC();
            return tab5;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}