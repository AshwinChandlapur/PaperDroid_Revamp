package vadeworks.news.paperdroids.AsiaNet.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class ViewPagerAdapter_AN extends FragmentStatePagerAdapter {

    private final CharSequence[] Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    private final int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_AN(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_AN tab1= new Tab1_Headlines_AN();
            return tab1;
        }
        else  if(position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab2_Sports_AN tab2 = new Tab2_Sports_AN();
            return tab2;
        }
        else  if(position == 2)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab3_Cinema_AN tab3 = new Tab3_Cinema_AN();
            return tab3;
        }
        else if(position ==3)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab4_Technology_AN tab4 = new Tab4_Technology_AN();
            return tab4;
        }else {
            Tab5_LifeStyle_AN tab5 = new Tab5_LifeStyle_AN();
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