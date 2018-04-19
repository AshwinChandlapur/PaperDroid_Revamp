package vadeworks.news.paperdroids;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
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
import vadeworks.news.paperdroids.HindustanTimes.HindustanTimes_Activity;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.paperdroid.BuildConfig;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 15/04/18.
 */

public class Utils {


    private static final String CARD_VIEW_VISIBILITY_VK = "card_view_visibility_vk";
    private static final String CARD_VIEW_VISIBILITY_PJ = "card_view_visibility_pj";
    private static final String CARD_VIEW_VISIBILITY_VV = "card_view_visibility_vv";
    private static final String CARD_VIEW_VISIBILITY_UV = "card_view_visibility_uv";
    private static final String CARD_VIEW_VISIBILITY_AN = "card_view_visibility_an";
    private static final String CARD_VIEW_VISIBILITY_ES = "card_view_visibility_es";
    private static final String CARD_VIEW_VISIBILITY_DH = "card_view_visibility_dh";
    private static final String CARD_VIEW_VISIBILITY_HT = "card_view_visibility_ht";
    private final Bundle params = new Bundle();
    public Activity activity;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    FrameLayout intent_to_allTerms, intent_to_home, intent_to_deccan, intent_to_hindustan, intent_to_vijayavaani, intent_to_vijayakarnataka, intent_to_prajavani, intent_to_udayavaani, intent_to_suvarna, intent_to_esanje, intent_to_asianet;
    String card_clicked;
    private FirebaseAnalytics mFirebaseAnalytics;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager pager;

    public Utils(Activity _activity) {
        this.activity = _activity;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.activity);
    }


    public void fetchCard(Context context) {

//        long cacheExpiration = 0;

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        long cacheExpiration = 24 * 60 * 60; // 1 Day

        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                displayPJ(mFirebaseRemoteConfig, intent_to_prajavani);
                displayVV(mFirebaseRemoteConfig, intent_to_vijayavaani);
                displayVK(mFirebaseRemoteConfig, intent_to_vijayakarnataka);
                displayUV(mFirebaseRemoteConfig, intent_to_udayavaani);
                displayAN(mFirebaseRemoteConfig, intent_to_asianet);
                displayES(mFirebaseRemoteConfig, intent_to_esanje);
                displayHT(mFirebaseRemoteConfig, intent_to_hindustan);
                displayDH(mFirebaseRemoteConfig, intent_to_deccan);
            }
        });
    }


    private void displayVK(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_vijayakarnataka) {

        if (intent_to_vijayakarnataka != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VK)) {
                intent_to_vijayakarnataka.setVisibility(View.VISIBLE);
            } else {
                intent_to_vijayakarnataka.setVisibility(View.GONE);
            }
        }
    }

    private void displayPJ(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_prajavani) {
        if (intent_to_prajavani != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_PJ)) {
                intent_to_prajavani.setVisibility(View.VISIBLE);
            } else {
                intent_to_prajavani.setVisibility(View.GONE);
            }
        }

    }

    private void displayVV(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_vijayavaani) {

        if (intent_to_vijayavaani != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VV)) {
                intent_to_vijayavaani.setVisibility(View.VISIBLE);
            } else {
                intent_to_vijayavaani.setVisibility(View.GONE);
            }
        }
    }

    private void displayUV(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_udayavaani) {

        if (intent_to_udayavaani != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_UV)) {
                intent_to_udayavaani.setVisibility(View.VISIBLE);
            } else {
                intent_to_udayavaani.setVisibility(View.GONE);
            }
        }
    }

    private void displayAN(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_suvarna) {

        if (intent_to_suvarna != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_AN)) {
                intent_to_suvarna.setVisibility(View.VISIBLE);
            } else {
                intent_to_suvarna.setVisibility(View.GONE);
            }
        }
    }

    private void displayES(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_esanje) {

        if (intent_to_esanje != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_ES)) {
                intent_to_esanje.setVisibility(View.VISIBLE);
            } else {
                intent_to_esanje.setVisibility(View.GONE);
            }
        }
    }

    private void displayDH(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_deccan) {

        if (intent_to_deccan != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_DH)) {
                intent_to_deccan.setVisibility(View.VISIBLE);
            } else {
                intent_to_deccan.setVisibility(View.GONE);
            }
        }
    }

    private void displayHT(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_hindustan) {

        if (intent_to_hindustan != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_HT)) {
                intent_to_hindustan.setVisibility(View.VISIBLE);
            } else {
                intent_to_hindustan.setVisibility(View.GONE);
            }
        }
    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }


    public AlertDialog.Builder buildDialog(final Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater factory = LayoutInflater.from(c);
        final View view = factory.inflate(R.layout.no_internet, null);
        Button wifi = view.findViewById(R.id.switchWifi);
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


        Button data = view.findViewById(R.id.switchData);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });

        builder.setView(view);
        return builder;
    }


    public void onClickers(final Context context, final DrawerLayout mDrawerLayout, final String tag) {

        intent_to_home = (FrameLayout) this.activity.findViewById(R.id.nav_home);
        intent_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.home.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, MainScreen_Activity.class);
                    context.startActivity(intent);
                }


            }
        });

        intent_to_prajavani = (FrameLayout) this.activity.findViewById(R.id.nav_prajavani);
        intent_to_prajavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_pj_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.pj.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, PrajaVaani_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_vijayavaani = (FrameLayout) this.activity.findViewById(R.id.nav_vijayavani);
        intent_to_vijayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_vv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);

                if (Constants.vv.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, VijayaVaani_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });


        intent_to_vijayakarnataka = (FrameLayout) this.activity.findViewById(R.id.nav_vijayakarnataka);
        intent_to_vijayakarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_vk_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.vk.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, VijayaKarnataka_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_udayavaani = (FrameLayout) this.activity.findViewById(R.id.nav_udayavaani);
        intent_to_udayavaani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_uv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.uv.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, UdayaVaani_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_suvarna = (FrameLayout) this.activity.findViewById(R.id.nav_suvarna);
        intent_to_suvarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_an_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.an.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, AsiaNet_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });


        intent_to_esanje = (FrameLayout) this.activity.findViewById(R.id.nav_esanje);
        intent_to_esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_es_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.es.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {

                }
            }
        });

        intent_to_allTerms = (FrameLayout) this.activity.findViewById(R.id.nav_about);
        intent_to_allTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_ab_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.at.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, All_Terms_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });


        intent_to_deccan = (FrameLayout) this.activity.findViewById(R.id.nav_deccan);
        intent_to_deccan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_dh);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.dh.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, DeccanHerald_Activiy.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_hindustan = (FrameLayout) this.activity.findViewById(R.id.nav_hindustantimes);
        intent_to_hindustan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_ht);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.ht.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, HindustanTimes_Activity.class);
                    context.startActivity(intent);
                }
            }
        });

    }

}


