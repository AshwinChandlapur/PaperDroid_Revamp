package vadeworks.news.paperdroids;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.net.PortUnreachableException;

import vadeworks.news.paperdroids.AsiaNet.tabs.ViewPagerAdapter_AN;
import vadeworks.news.paperdroids.app_skeleton.sliding.SlidingTabLayout;
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
    FirebaseRemoteConfig mFirebaseRemoteConfig;


    public void fetchCard(Context context, final FrameLayout intent_to_prajavani,
                          final FrameLayout intent_to_vijayavaani,final FrameLayout intent_to_vijayakarnataka,
                          final FrameLayout intent_to_udayavaani,final FrameLayout intent_to_asianet,
                          final FrameLayout intent_to_esanje,final FrameLayout intent_to_hindustan,final FrameLayout intent_to_deccan) {

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
                displayPJ(mFirebaseRemoteConfig,intent_to_prajavani);
                displayVV(mFirebaseRemoteConfig,intent_to_vijayavaani);
                displayVK(mFirebaseRemoteConfig,intent_to_vijayakarnataka);
                displayUV(mFirebaseRemoteConfig,intent_to_udayavaani);
                displayAN(mFirebaseRemoteConfig,intent_to_asianet);
                displayES(mFirebaseRemoteConfig,intent_to_esanje);
                displayHT(mFirebaseRemoteConfig,intent_to_hindustan);
                displayDH(mFirebaseRemoteConfig,intent_to_deccan);
            }
        });
    }


    private void displayVK(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_vijayakarnataka) {

        if(intent_to_vijayakarnataka!=null){
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VK)) {
                intent_to_vijayakarnataka.setVisibility(View.VISIBLE);
            } else {
                intent_to_vijayakarnataka.setVisibility(View.GONE);
            }
        }
    }

    private void displayPJ(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_prajavani) {
        if(intent_to_prajavani!=null){
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_PJ)) {
                intent_to_prajavani.setVisibility(View.VISIBLE);
            } else {
                intent_to_prajavani.setVisibility(View.GONE);
            }
        }

    }

    private void displayVV(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_vijayavaani) {

        if(intent_to_vijayavaani!=null){
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VV)) {
                intent_to_vijayavaani.setVisibility(View.VISIBLE);
            } else {
                intent_to_vijayavaani.setVisibility(View.GONE);
            }
        }
    }

    private void displayUV(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_udayavaani) {

        if(intent_to_udayavaani!=null){
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_UV)) {
                intent_to_udayavaani.setVisibility(View.VISIBLE);
            } else {
                intent_to_udayavaani.setVisibility(View.GONE);
            }
        }
    }

    private void displayAN(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_suvarna) {

        if(intent_to_suvarna!=null){
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_AN)) {
                intent_to_suvarna.setVisibility(View.VISIBLE);
            } else {
                intent_to_suvarna.setVisibility(View.GONE);
            }
        }
    }

    private void displayES(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_esanje) {

        if(intent_to_esanje!=null){
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_ES)) {
                intent_to_esanje.setVisibility(View.VISIBLE);
            } else {
                intent_to_esanje.setVisibility(View.GONE);
            }
        }
    }

    private void displayDH(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_deccan) {

        if(intent_to_deccan!=null)
        {
            if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_DH)) {
                intent_to_deccan.setVisibility(View.VISIBLE);
            } else {
                intent_to_deccan.setVisibility(View.GONE);
            }
        }
    }

    private void displayHT(FirebaseRemoteConfig mFirebaseRemoteConfig,FrameLayout intent_to_hindustan) {

        if(intent_to_hindustan!=null){
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





}


