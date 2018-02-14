package vadeworks.news.paperdroids.Prajavani.tabs;

/**
 * Created by ashwinchandlapur on 14/02/18.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vadeworks.news.paperdroids.UdayaVaani.tabs.Tab1_Headlines_UV;
import vadeworks.news.paperdroids.UdayaVaani.tabs.Tab2_Cinema_UV;
import vadeworks.news.paperdroids.VijayaKarnataka.tabs.Tab1_Headlines_VK;
import vadeworks.news.paperdroids.VijayaKarnataka.tabs.Tab2_Sports_VK;
import vadeworks.news.paperdroids.VijayaKarnataka.tabs.Tab3_Cinema_VK;
import vadeworks.news.paperdroids.VijayaKarnataka.tabs.Tab4_Lifestyle_VK;
import vadeworks.news.paperdroids.VijayaKarnataka.tabs.Tab5_Technology_VK;

public class ViewPagerAdapter_PJ extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_PJ(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_PJ tab1 = new Tab1_Headlines_PJ();
            return tab1;
        }
        else if (position == 1){
            Tab2 tab2 = new Tab2();
            return tab2;
        }
        else if(position ==2){
            Tab3 tab3 = new Tab3();
            return tab3;
        }
        else {
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