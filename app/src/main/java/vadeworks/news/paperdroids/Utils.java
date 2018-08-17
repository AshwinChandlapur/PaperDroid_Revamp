package vadeworks.news.paperdroids;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import vadeworks.news.paperdroids.English.DNA.Dna_Activity;
import vadeworks.news.paperdroids.English.DeccanHerald.DeccanHerald_Activity;
import vadeworks.news.paperdroids.English.HindustanTimes.HindustanTimes_Activity;
import vadeworks.news.paperdroids.English.IndianExpress.IndianExpress_Activity;
import vadeworks.news.paperdroids.Hindi.AajTak.AajTak_MainActivity;
import vadeworks.news.paperdroids.Hindi.Bbc.BBC_MainActivity;
import vadeworks.news.paperdroids.Hindi.Jagaran.Jagaran_MainActivity;
import vadeworks.news.paperdroids.Hindi.Ndtv.Ndtv_MainActivity;
import vadeworks.news.paperdroids.Kannada.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Kannada.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Kannada.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.Kannada.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.paperdroid.BuildConfig;
import vadeworks.paperdroid.R;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by ashwinchandlapur on 15/04/18.
 */

public class Utils {


    private static final String CARD_VIEW_VISIBILITY_VK = "card_view_visibility_vk";
    private static final String CARD_VIEW_VISIBILITY_PJ = "card_view_visibility_pj";
    private static final String CARD_VIEW_VISIBILITY_VV = "card_view_visibility_vv";
    private static final String CARD_VIEW_VISIBILITY_ES = "card_view_visibility_es";

    private static final String CARD_VIEW_VISIBILITY_DH = "card_view_visibility_dh";
    private static final String CARD_VIEW_VISIBILITY_HT = "card_view_visibility_ht";
    private static final String CARD_VIEW_VISIBILITY_IE = "card_view_visibility_ie";
    private static final String CARD_VIEW_VISIBILITY_DNA = "card_view_visibility_dna";

    private static final String CARD_VIEW_VISIBILITY_NDTV = "card_view_visibility_ndtv";
    private static final String CARD_VIEW_VISIBILITY_AT = "card_view_visibility_at";
    private static final String CARD_VIEW_VISIBILITY_JG = "card_view_visibility_jg";
    private static final String CARD_VIEW_VISIBILITY_BBC = "card_view_visibility_bbc";


    private final Bundle params = new Bundle();
    public Activity activity;
    FirebaseRemoteConfig mFirebaseRemoteConfig;
    FrameLayout intent_to_allTerms, intent_to_home, intent_to_deccan, intent_to_hindustan,
            intent_to_vijayavaani, intent_to_vijayakarnataka, intent_to_prajavani,
            intent_to_esanje, intent_to_indianexpress, intent_to_dna, intent_to_ndtv, intent_to_aajtak, intent_to_jagaran, intent_to_bbc;

    String card_clicked;
    private FirebaseAnalytics mFirebaseAnalytics;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager pager;

    public Utils(Activity _activity) {
        this.activity = _activity;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.activity);
    }


//    public void clearAppData(Context context) {
//        try {
//            // clearing app data
//            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
//                ((ActivityManager)context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
//            } else {
//                String packageName = context.getApplicationContext().getPackageName();
//                Runtime runtime = Runtime.getRuntime();
//                runtime.exec("pm clear "+packageName);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void fetchCard(Context context) {

//        long cacheExpiration = 0;
        intent_to_prajavani = this.activity.findViewById(R.id.nav_prajavani);
        intent_to_esanje = this.activity.findViewById(R.id.nav_esanje);
        intent_to_vijayakarnataka = this.activity.findViewById(R.id.nav_vijayakarnataka);
        intent_to_vijayavaani = this.activity.findViewById(R.id.vijayavani);

        intent_to_deccan = this.activity.findViewById(R.id.nav_deccan);
        intent_to_hindustan = this.activity.findViewById(R.id.nav_hindustantimes);
        intent_to_indianexpress = this.activity.findViewById(R.id.nav_indianexpress);
        intent_to_dna = this.activity.findViewById(R.id.nav_dna);

        intent_to_ndtv = this.activity.findViewById(R.id.nav_ndtv);
        intent_to_aajtak = this.activity.findViewById(R.id.nav_aajtak);
        intent_to_jagaran = this.activity.findViewById(R.id.nav_jagaran);
        intent_to_bbc = this.activity.findViewById(R.id.nav_bbc);

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
                displayES(mFirebaseRemoteConfig, intent_to_esanje);

                displayHT(mFirebaseRemoteConfig, intent_to_hindustan);
                displayDH(mFirebaseRemoteConfig, intent_to_deccan);
                displayIE(mFirebaseRemoteConfig, intent_to_indianexpress);
                displayDNA(mFirebaseRemoteConfig, intent_to_dna);

                displayNDTV(mFirebaseRemoteConfig, intent_to_ndtv);
                displayAT(mFirebaseRemoteConfig, intent_to_aajtak);
                displayJG(mFirebaseRemoteConfig, intent_to_jagaran);
                displayBBC(mFirebaseRemoteConfig, intent_to_bbc);

            }
        });
    }

    private void displayNDTV(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_ndtv) {

        if (intent_to_ndtv != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_NDTV)) {
                intent_to_ndtv.setVisibility(View.VISIBLE);
            } else {
                intent_to_ndtv.setVisibility(View.GONE);
            }
        }
    }

    private void displayAT(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_aajtak) {

        if (intent_to_aajtak != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_AT)) {
                intent_to_aajtak.setVisibility(View.VISIBLE);
            } else {
                intent_to_aajtak.setVisibility(View.GONE);
            }
        }
    }

    private void displayJG(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_jagaran) {

        if (intent_to_jagaran != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_JG)) {
                intent_to_jagaran.setVisibility(View.VISIBLE);
            } else {
                intent_to_jagaran.setVisibility(View.GONE);
            }
        }
    }


    private void displayBBC(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_bbc) {

        if (intent_to_bbc != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_BBC)) {
                intent_to_bbc.setVisibility(View.VISIBLE);
            } else {
                intent_to_bbc.setVisibility(View.GONE);
            }
        }
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

    private void displayIE(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_indianexpress) {

        if (intent_to_indianexpress != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_IE)) {
                intent_to_indianexpress.setVisibility(View.VISIBLE);
            } else {
                intent_to_indianexpress.setVisibility(View.GONE);
            }
        }
    }

    private void displayDNA(FirebaseRemoteConfig mFirebaseRemoteConfig, FrameLayout intent_to_dna) {

        if (intent_to_dna != null) {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_DNA)) {
                intent_to_dna.setVisibility(View.VISIBLE);
            } else {
                intent_to_dna.setVisibility(View.GONE);
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

        intent_to_home = this.activity.findViewById(R.id.nav_home);
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

        intent_to_prajavani = this.activity.findViewById(R.id.nav_prajavani);
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

        intent_to_vijayavaani = this.activity.findViewById(R.id.nav_vijayavani);
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


        intent_to_vijayakarnataka = this.activity.findViewById(R.id.nav_vijayakarnataka);
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

        intent_to_esanje = this.activity.findViewById(R.id.nav_esanje);
        intent_to_esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_es_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.es.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, Esanje_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });


        intent_to_allTerms = this.activity.findViewById(R.id.nav_about);
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


        intent_to_deccan = this.activity.findViewById(R.id.nav_deccan);
        intent_to_deccan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_dh);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.dh.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, DeccanHerald_Activity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_hindustan = this.activity.findViewById(R.id.nav_hindustantimes);
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

        intent_to_indianexpress = this.activity.findViewById(R.id.nav_indianexpress);
        intent_to_indianexpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_ie);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.ie.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, IndianExpress_Activity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_dna = this.activity.findViewById(R.id.nav_dna);
        intent_to_dna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_dna);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.dna.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, Dna_Activity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_ndtv = this.activity.findViewById(R.id.nav_ndtv);
        intent_to_ndtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_ndtv);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.ndtv.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, Ndtv_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_aajtak = this.activity.findViewById(R.id.nav_aajtak);
        intent_to_aajtak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_aajtak);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.at.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, AajTak_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        intent_to_jagaran = this.activity.findViewById(R.id.nav_jagaran);
        intent_to_jagaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_jagaran);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.jg.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, Jagaran_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });


        intent_to_bbc = this.activity.findViewById(R.id.nav_bbc);
        intent_to_bbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = context.getResources().getString(R.string.toolbar_title_home_bbc);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                if (Constants.bbc.equals(tag)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    Intent intent = new Intent(context, BBC_MainActivity.class);
                    context.startActivity(intent);
                }
            }
        });


    }

}


