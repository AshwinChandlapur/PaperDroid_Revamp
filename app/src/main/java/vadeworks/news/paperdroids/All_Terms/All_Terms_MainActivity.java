package vadeworks.news.paperdroids.All_Terms;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import vadeworks.news.paperdroids.All_Terms.tabs.ViewPagerAdapter_AT;
import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.AsiaNet.tabs.ViewPagerAdapter_AN;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Splash_Screen.Splash_Main_Activity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.news.paperdroids.app_skeleton.customViews.ScrimInsetsFrameLayout;
import vadeworks.news.paperdroids.app_skeleton.sliding.SlidingTabLayout;
import vadeworks.news.paperdroids.app_skeleton.utils.UtilsDevice;
import vadeworks.news.paperdroids.app_skeleton.utils.UtilsMiscellaneous;
import vadeworks.paperdroid.R;

public class All_Terms_MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter_AT adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Feedback","Privacy Policy","Terms","Disclaimer"};
    int Numboftabs =4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_terms_mainactivity);
        init_slider();

        init_navigator();



        FrameLayout intent_to_home = (FrameLayout)findViewById(R.id.nav_home);
        intent_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Terms_MainActivity.this, MainScreen_Activity.class);
                startActivity(intent);

            }
        });

        FrameLayout intent_to_prajavani = (FrameLayout)findViewById(R.id.nav_prajavani);
        intent_to_prajavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Terms_MainActivity.this, PrajaVaani_MainActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_vijayavaani = (FrameLayout)findViewById(R.id.nav_vijayavani);
        intent_to_vijayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Terms_MainActivity.this, VijayaVaani_MainActivity.class);
                startActivity(intent);
            }
        });


        FrameLayout intent_to_vijayakarnataka = (FrameLayout)findViewById(R.id.nav_vijayakarnataka);
        intent_to_vijayakarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Terms_MainActivity.this, VijayaKarnataka_MainActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_udayavaani = (FrameLayout)findViewById(R.id.nav_udayavaani);
        intent_to_udayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Terms_MainActivity.this, UdayaVaani_MainActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_suvarna = (FrameLayout)findViewById(R.id.nav_suvarna);
        intent_to_suvarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(All_Terms_MainActivity.this, AsiaNet_MainActivity.class);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"You are on the same Page",Toast.LENGTH_LONG).show();
//                mDrawerLayout.closeDrawers();
//                Log.d("Clicked","Cliked in same category");
            }
        });


        FrameLayout intent_to_esanje = (FrameLayout)findViewById(R.id.nav_esanje);
        intent_to_esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(All_Terms_MainActivity.this, Esanje_MainActivity.class);
                startActivity(intent);
            }
        });


        FrameLayout intent_to_allTerms = (FrameLayout)findViewById(R.id.nav_about);
        intent_to_allTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });




    }


    private void init_slider() {
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter_AN and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter_AT(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;

    private void init_navigator(){
        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        toolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                )
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);
        // Set the first item as selected for the first time
        getSupportActionBar().setTitle("About");
//        getSupportActionBar().setIcon(getApplicationContext().getResources().getDrawable(R.drawable.an));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(All_Terms_MainActivity.this, MainScreen_Activity.class);
            startActivity(intent);
        }
        return true;
    }





}