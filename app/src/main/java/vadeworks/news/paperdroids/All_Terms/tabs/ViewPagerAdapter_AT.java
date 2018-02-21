package vadeworks.news.paperdroids.All_Terms.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vadeworks.news.paperdroids.AsiaNet.tabs.Tab1_Headlines_AN;
import vadeworks.news.paperdroids.AsiaNet.tabs.Tab2_Sports_AN;
import vadeworks.news.paperdroids.AsiaNet.tabs.Tab3_Cinema_AN;
import vadeworks.news.paperdroids.AsiaNet.tabs.Tab4_Technology_AN;
import vadeworks.news.paperdroids.AsiaNet.tabs.Tab5_LifeStyle_AN;

/**
 * Created by ashwinchandlapur on 21/02/18.
 */


public class ViewPagerAdapter_AT extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_AT(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1 tab1= new Tab1();
            return tab1;
        }
        else if(position ==1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab2 tab2 = new Tab2();
            return tab2;
        }
        else if (position ==2){
            Tab3 tab3 = new Tab3();
            return tab3;
        }else{
            Tab4 tab4 = new Tab4();
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