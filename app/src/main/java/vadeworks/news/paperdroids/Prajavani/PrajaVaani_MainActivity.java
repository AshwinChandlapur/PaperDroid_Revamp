package vadeworks.news.paperdroids.Prajavani;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import vadeworks.news.paperdroids.All_Terms.All_Terms_MainActivity;
import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.DeccanHerald.DeccanHerald_Activiy;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.HindustanTimes.HindustanTimes_Activity;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.Prajavani.tabs.ViewPagerAdapter_PJ;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.news.paperdroids.app_skeleton.customViews.ScrimInsetsFrameLayout;
import vadeworks.news.paperdroids.app_skeleton.sliding.SlidingTabLayout;
import vadeworks.news.paperdroids.app_skeleton.utils.UtilsDevice;
import vadeworks.news.paperdroids.app_skeleton.utils.UtilsMiscellaneous;
import vadeworks.paperdroid.BuildConfig;
import vadeworks.paperdroid.R;

public class PrajaVaani_MainActivity extends AppCompatActivity {

    private static final String CARD_VIEW_VISIBILITY_VK = "card_view_visibility_vk";
    private static final String CARD_VIEW_VISIBILITY_PJ = "card_view_visibility_pj";
    private static final String CARD_VIEW_VISIBILITY_VV = "card_view_visibility_vv";
    private static final String CARD_VIEW_VISIBILITY_UV = "card_view_visibility_uv";
    private static final String CARD_VIEW_VISIBILITY_AN = "card_view_visibility_an";
    private static final String CARD_VIEW_VISIBILITY_ES = "card_view_visibility_es";
    private static final String CARD_VIEW_VISIBILITY_DH = "card_view_visibility_dh";
    private static final String CARD_VIEW_VISIBILITY_HT = "card_view_visibility_ht";
    private final CharSequence[] Titles = {"ಮುಖ್ಯಾಂಶಗಳು", "ರಾಜ್ಯ", "ದೇಶ", "ಕ್ರೀಡೆ", "ಸಿನಿಮಾ", "ವಾಣಿಜ್ಯ"};
    private final int Numboftabs = 6;
    private final Bundle params = new Bundle();
    FrameLayout intent_to_deccan, intent_to_hindustan, intent_to_vijayavaani, intent_to_vijayakarnataka, intent_to_prajavani, intent_to_udayavaani, intent_to_suvarna, intent_to_esanje;
    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter_PJ adapter;
    private SlidingTabLayout tabs;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String card_clicked;
    private DrawerLayout mDrawerLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prajavani_mainactivity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        fetchCard();


        if (!isConnected(this)) {
            buildDialog(this).show();

        } else {
            Log.d("Internet Working", "Internet Working");
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }


        init_slider();

        init_navigator();


        FrameLayout intent_to_home = findViewById(R.id.nav_home);
        intent_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, MainScreen_Activity.class);
                startActivity(intent);

            }
        });

        intent_to_prajavani = findViewById(R.id.nav_prajavani);
        intent_to_prajavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"You are on the same Page",Toast.LENGTH_LONG).show();
                mDrawerLayout.closeDrawers();
                Log.d("Clicked", "Cliked in same category");
            }
        });

        intent_to_vijayavaani = findViewById(R.id.nav_vijayavani);
        intent_to_vijayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_vv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, VijayaVaani_MainActivity.class);
                startActivity(intent);
            }
        });


        intent_to_vijayakarnataka = findViewById(R.id.nav_vijayakarnataka);
        intent_to_vijayakarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_vk_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, VijayaKarnataka_MainActivity.class);
                startActivity(intent);
            }
        });

        intent_to_udayavaani = findViewById(R.id.nav_udayavaani);
        intent_to_udayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_uv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, UdayaVaani_MainActivity.class);
                startActivity(intent);
            }
        });

        intent_to_suvarna = findViewById(R.id.nav_suvarna);
        intent_to_suvarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_an_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, AsiaNet_MainActivity.class);
                startActivity(intent);
            }
        });


        intent_to_esanje = findViewById(R.id.nav_esanje);
        intent_to_esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_es_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, Esanje_MainActivity.class);
                startActivity(intent);
            }
        });


        intent_to_deccan = findViewById(R.id.nav_deccan);
        intent_to_deccan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_dh);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, DeccanHerald_Activiy.class);
                startActivity(intent);
            }
        });

        intent_to_hindustan = findViewById(R.id.nav_hindustantimes);
        intent_to_hindustan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_ht);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, HindustanTimes_Activity.class);
                startActivity(intent);
            }
        });

        FrameLayout intent_to_allTerms = findViewById(R.id.nav_about);
        intent_to_allTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_ab_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent intent = new Intent(PrajaVaani_MainActivity.this, All_Terms_MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void init_slider() {
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter_AN and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapter_PJ(getSupportFragmentManager(), Titles, Numboftabs);

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
        getSupportActionBar().setTitle(R.string.toolbar_title_home_pj);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(PrajaVaani_MainActivity.this, MainScreen_Activity.class);
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
//                finish();//Goes back to activity that was in stack.
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


        Button data = view.findViewById(R.id.switchData);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);
            }
        });


        builder.setView(view);
        return builder;
    }

    private void fetchCard() {

//        long cacheExpiration = 0;

        long cacheExpiration = 24 * 60 * 60; // 1 Day

        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        }
                        displayPJ();
                        displayVV();
                        displayVK();
                        displayUV();
                        displayAN();
                        displayES();
                        displayHT();
                        displayDH();
                    }
                });
        // [END fetch_config_with_callback]
    }


    private void displayVK() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VK)) {
            intent_to_vijayakarnataka.setVisibility(View.VISIBLE);
        } else {
            intent_to_vijayakarnataka.setVisibility(View.GONE);
        }
    }

    private void displayPJ() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_PJ)) {
            intent_to_prajavani.setVisibility(View.VISIBLE);
        } else {
            intent_to_prajavani.setVisibility(View.GONE);
        }
    }

    private void displayVV() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VV)) {
            intent_to_vijayavaani.setVisibility(View.VISIBLE);
        } else {
            intent_to_vijayavaani.setVisibility(View.GONE);
        }
    }

    private void displayUV() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_UV)) {
            intent_to_udayavaani.setVisibility(View.VISIBLE);
        } else {
            intent_to_udayavaani.setVisibility(View.GONE);
        }
    }

    private void displayAN() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_AN)) {
            intent_to_suvarna.setVisibility(View.VISIBLE);
        } else {
            intent_to_suvarna.setVisibility(View.GONE);
        }
    }

    private void displayES() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_ES)) {
            intent_to_esanje.setVisibility(View.VISIBLE);
        } else {
            intent_to_esanje.setVisibility(View.GONE);
        }
    }

    private void displayDH() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_DH)) {
            intent_to_deccan.setVisibility(View.VISIBLE);
        } else {
            intent_to_deccan.setVisibility(View.GONE);
        }
    }

    private void displayHT() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_HT)) {
            intent_to_hindustan.setVisibility(View.VISIBLE);
        } else {
            intent_to_hindustan.setVisibility(View.GONE);
        }
    }


}
