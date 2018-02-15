package vadeworks.news.paperdroids.UdayaVaani.tabs;

/**
 * Created by ashwinchandlapur on 12/02/18.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter_UV extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_UV(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_UV tab1 = new Tab1_Headlines_UV();
            return tab1;
        }
        else if(position == 1) {
            Tab2_Cinema_UV tab2 = new Tab2_Cinema_UV();
            return tab2;
        }
        else if (position ==2){
            Tab3_Sports_UV tab3 = new Tab3_Sports_UV();
            return tab3;
        }
        else if(position ==4){
            Tab4_World_UV tab4 = new Tab4_World_UV();
            return tab4;
        }else{
            Tab5_Business_UV tab5 =new Tab5_Business_UV();
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