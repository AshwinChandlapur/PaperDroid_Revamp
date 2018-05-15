package vadeworks.news.paperdroids.English.IndianExpress.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class ViewPagerAdapter_IE extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_IE(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_IE tab1 = new Tab1_Headlines_IE();
            return tab1;
        } else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab2_India_IE tab2 = new Tab2_India_IE();
            return tab2;
        } else if (position == 2)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab3_Sports_IE tab3 = new Tab3_Sports_IE();
            return tab3;
        } else if (position == 3)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab4_Entertainment_IE tab4 = new Tab4_Entertainment_IE();
            return tab4;
        } else {
            Tab5_World_IE tab5 = new Tab5_World_IE();
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