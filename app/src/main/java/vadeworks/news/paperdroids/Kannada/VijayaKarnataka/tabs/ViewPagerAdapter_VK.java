package vadeworks.news.paperdroids.Kannada.VijayaKarnataka.tabs;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter_VK extends FragmentStatePagerAdapter {

    private final CharSequence[] Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter_AN is created
    private final int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter_AN is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter_VK(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1_Headlines_VK tab1 = new Tab1_Headlines_VK();


            return tab1;

        } else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab2_Sports_VK tab2 = new Tab2_Sports_VK();
            return tab2;
        } else if (position == 2) {
            Tab3_Cinema_VK tab3 = new Tab3_Cinema_VK();
            return tab3;
        } else if (position == 3) {
            Tab4_Lifestyle_VK tab4 = new Tab4_Lifestyle_VK();
            return tab4;
        } else {
            Tab5_Technology_VK tab5 = new Tab5_Technology_VK();
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