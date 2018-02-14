package vadeworks.news.paperdroids.VijayaVaani.tabs;

/**
 * Created by ashwinchandlapur on 14/02/18.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;




public class ViewPagerAdapter_VV extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_VV(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_VV tab1 = new Tab1_Headlines_VV();
            return tab1;
        } else if (position == 1) {
            vadeworks.news.paperdroids.VijayaVaani.tabs.Tab2 tab2 = new vadeworks.news.paperdroids.VijayaVaani.tabs.Tab2();
            return tab2;
        } else if (position == 2) {
            vadeworks.news.paperdroids.VijayaVaani.tabs.Tab3 tab3 = new vadeworks.news.paperdroids.VijayaVaani.tabs.Tab3();
            return tab3;
        } else {
            vadeworks.news.paperdroids.VijayaVaani.tabs.Tab4 tab4 = new vadeworks.news.paperdroids.VijayaVaani.tabs.Tab4();
            return tab4;
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

