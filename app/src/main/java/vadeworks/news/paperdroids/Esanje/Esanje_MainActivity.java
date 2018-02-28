package vadeworks.news.paperdroids.Esanje;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.analytics.FirebaseAnalytics;

import vadeworks.news.paperdroids.All_Terms.All_Terms_MainActivity;
import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.Esanje.tabs.ViewPagerAdapter_ES;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.news.paperdroids.app_skeleton.customViews.ScrimInsetsFrameLayout;
import vadeworks.news.paperdroids.app_skeleton.sliding.SlidingTabLayout;
import vadeworks.news.paperdroids.app_skeleton.utils.UtilsDevice;
import vadeworks.news.paperdroids.app_skeleton.utils.UtilsMiscellaneous;
import vadeworks.paperdroid.R;

public class Esanje_MainActivity extends AppCompatActivity {

    private final CharSequence[] Titles = {"ಮುಖ್ಯಾಂಶಗಳು", "ರಾಜ್ಯ", "ದೇಶ", "ಸಿನಿಮಾ", "ಕ್ರೀಡೆ", "ವಾಣಿಜ್ಯ"};
    private final int Numboftabs = 6;
    private final Bundle params = new Bundle();
    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter_ES adapter;
    private SlidingTabLayout tabs;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String card_clicked;
    private DrawerLayout mDrawerLayout;

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esanje_mainactivity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        init_slider();

        init_navigator();


        if (!isConnected(this)) {
            buildDialog(this).show();

        } else {
            Log.d("Internet Working", "Internet Working");
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }


        FrameLayout intent_to_home = findViewById(R.id.nav_home);
        intent_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, MainScreen_Activity.class);
                startActivity(intent);

            }
        });

        FrameLayout intent_to_prajavani = findViewById(R.id.nav_prajavani);
        intent_to_prajavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_pj_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, PrajaVaani_MainActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_vijayavaani = findViewById(R.id.nav_vijayavani);
        intent_to_vijayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_vv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, VijayaVaani_MainActivity.class);
                startActivity(intent);
            }
        });


        FrameLayout intent_to_vijayakarnataka = findViewById(R.id.nav_vijayakarnataka);
        intent_to_vijayakarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_vk_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, VijayaKarnataka_MainActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_udayavaani = findViewById(R.id.nav_udayavaani);
        intent_to_udayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_uv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, UdayaVaani_MainActivity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_suvarna = findViewById(R.id.nav_suvarna);
        intent_to_suvarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_an_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, AsiaNet_MainActivity.class);
                startActivity(intent);
            }
        });


        FrameLayout intent_to_esanje = findViewById(R.id.nav_esanje);
        intent_to_esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"You are on the same Page",Toast.LENGTH_LONG).show();
                mDrawerLayout.closeDrawers();
                Log.d("Clicked", "Cliked in same category");
            }
        });

        FrameLayout intent_to_allTerms = findViewById(R.id.nav_about);
        intent_to_allTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_ab_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(Esanje_MainActivity.this, All_Terms_MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void init_slider() {
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter_AN and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter_ES(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);//TODO : Based on Network (2G,3G,4G) set the no of screens to load.
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = findViewById(R.id.tabs);
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

    private void init_navigator() {
        // Navigation Drawer
        mDrawerLayout = findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primaryDark));
        mScrimInsetsFrameLayout = findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        toolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                ) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null) {
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
        getSupportActionBar().setTitle(R.string.toolbar_title_home_es);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Esanje_MainActivity.this, MainScreen_Activity.class);
            startActivity(intent);
        }
        return true;
    }


    private boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater factory = LayoutInflater.from(c);
        final View view = factory.inflate(R.layout.no_internet, null);
        Button wifi = view.findViewById(R.id.switchWifi);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


        Button data = view.findViewById(R.id.switchData);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);
            }
        });


        builder.setView(view);
        return builder;
    }


}
