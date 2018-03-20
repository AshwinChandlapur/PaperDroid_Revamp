package vadeworks.news.paperdroids.MainScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.Date;

import vadeworks.news.paperdroids.Articles;
import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.DeccanHerald.DeccanHerald_Activiy;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Exclusive.ExclusiveActivity;
import vadeworks.news.paperdroids.HindustanTimes.HindustanTimes_Activity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VerticalNews.Vertical_News;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.paperdroid.BuildConfig;
import vadeworks.paperdroid.R;

public class MainScreen_Activity extends AppCompatActivity {


    private static final String CARD_VIEW_VISIBILITY_VK = "card_view_visibility_vk";
    private static final String CARD_VIEW_VISIBILITY_PJ = "card_view_visibility_pj";
    private static final String CARD_VIEW_VISIBILITY_VV = "card_view_visibility_vv";
    private static final String CARD_VIEW_VISIBILITY_UV = "card_view_visibility_uv";
    private static final String CARD_VIEW_VISIBILITY_AN = "card_view_visibility_an";
    private static final String CARD_VIEW_VISIBILITY_ES = "card_view_visibility_es";
    private static final String CARD_VIEW_VISIBILITY_HT = "card_view_visibility_ht";
    private static final String CARD_VIEW_VISIBILITY_DH = "card_view_visibility_dh";
    private final Bundle params = new Bundle();
    String carat22, carat24, petrol, diesel;
    int result;
    private CardView prajavani;
    private CardView vijayavani;
    private CardView vijayakarnataka;
    private CardView udayavani;
    private CardView suvarna;
    private CardView esanje;
    private CardView deccanherald;
    private CardView hindustantimes;
    private View parentLayout;
    private ImageView bottomImage,exclusive_background_image ;
    private TextView gold22_textview, gold24_textview, petrol_textview, diesel_textview, airNo_textview, airQuality_textview;
    private int click = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String card_clicked;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    Articles todisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        parentLayout = findViewById(android.R.id.content);
        parentLayout.setFocusableInTouchMode(true);
        parentLayout.requestFocus();
        parentLayout.setFocusableInTouchMode(false);

        prajavani = findViewById(R.id.prajavani);
        vijayavani = findViewById(R.id.vijayavani);
        vijayakarnataka = findViewById(R.id.vijayakarnataka);
        udayavani = findViewById(R.id.udayavani);
        suvarna = findViewById(R.id.suvarna);
        esanje = findViewById(R.id.esanje);
        deccanherald = findViewById(R.id.deccanherald);
        hindustantimes = findViewById(R.id.hindustantimes);
        bottomImage = findViewById(R.id.bottomimage);
        exclusive_background_image = findViewById(R.id.exclusive_background);

        Picasso.with(this).load(Constants.exclusiveBackground).placeholder(R.drawable.kannada).error(R.drawable.kannada).into(exclusive_background_image);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        fetchCard();
        CardView overview_card = findViewById(R.id.overviewcard);
        FrameLayout locklayout = findViewById(R.id.locklayout);
        TextView locktxt = findViewById(R.id.locktext);
//        overview_card.setVisibility(View.GONE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Date firstlaunch = new Date((long) prefs.getLong("firstlaunch", ((long) System.currentTimeMillis() / 1000L)));
        Date currentDate = new Date(System.currentTimeMillis() / 1000L);
        int diffInDays = (int) ((currentDate.getTime() - firstlaunch.getTime()) / (60 * 60 * 24));
        Log.d("difference :", "" + diffInDays + ": " + currentDate.getTime() + ": " + firstlaunch.getTime());

        locktxt.setText("Kannada Kampu will be available in "+ (3 - diffInDays ) + " days...");
        //  if more than 3 days & not unlocked, set unlock status
        if ((!prefs.getBoolean("isunlocked", false)) && diffInDays >= Constants.UNLOCK_DAYS) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isunlocked", true);
            OneSignal.sendTag("unlock_status", "unlocked");
            editor.apply();
        }

        //if unlocked enable or disable feature
        if (prefs.getBoolean("isunlocked", false)) {
            locklayout.setVisibility(View.GONE);
            overview_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Card view ", "Clicked");
                    Intent i = new Intent(MainScreen_Activity.this, ExclusiveActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
            Log.d("shared pref", "Feature unlocked");
        }

        gold22_textview = findViewById(R.id.gold22);
        gold24_textview = findViewById(R.id.gold24);
        petrol_textview = findViewById(R.id.petrol);
        diesel_textview = findViewById(R.id.diesel);
        airNo_textview = findViewById(R.id.airNo);
        airQuality_textview = findViewById(R.id.airQuality);


        prajavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_pj_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, PrajaVaani_MainActivity.class);
                startActivity(i);
            }
        });

        vijayavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_vv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, VijayaVaani_MainActivity.class);
                startActivity(i);
            }
        });

        vijayakarnataka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_vk_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, VijayaKarnataka_MainActivity.class);
                startActivity(i);
            }
        });

        udayavani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_uv_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, UdayaVaani_MainActivity.class);
                startActivity(i);
            }
        });


        suvarna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_an_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, AsiaNet_MainActivity.class);
                startActivity(i);
            }
        });


        esanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_es_en);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, Esanje_MainActivity.class);
                startActivity(i);
            }
        });

        deccanherald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_dh);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, DeccanHerald_Activiy.class);
                startActivity(i);
            }
        });

        hindustantimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_ht);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, HindustanTimes_Activity.class);
                startActivity(i);
            }
        });



        bottomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = click + 1;
                if (click == 7) {
                    Snackbar.make(parentLayout, "Sherlock's Guess: You must be a Developer :D", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("gold thread", "run: thread for gold");
                    getRates();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getRates() {

        String goldratesUrl = "http://www.sify.com/finance/gold_rates/";
        org.jsoup.nodes.Document goldDoc;
        Elements goldElem24, goldElem22;


        try {
            //For Gold Rates
            goldDoc = Jsoup.connect(goldratesUrl).get();

            goldElem22 = goldDoc.getElementsByTag("tbody").select("tr:eq(1)").select("td:eq(7)");
            carat22 = "22ct: ₹" + goldElem22.text();

            goldElem24 = goldDoc.getElementsByTag("tbody").select("tr:eq(2)").select("td:eq(6)");
            carat24 = "24ct: ₹" + goldElem24.text();
            Log.d("rates are", "gold rates 24carrot   " + carat24);
            Log.d("rates are", "gold rates 22carrot   " + carat22);
        } catch (Exception e) {
            carat22 = "- -";
            carat24 = "- -";
        }

        try {
            // for AQI
            String aqiUrl = "http://aqicn.org/city/india/bangalore/city-railway-station/";

            org.jsoup.nodes.Document aqiDoc = Jsoup.connect(aqiUrl).get();
            Elements aqiElem = aqiDoc.select("td.aqiwgt-table-aqicell");

            result = 0;
            try {
                result = Integer.parseInt(aqiElem.text());
            } catch (NumberFormatException e) {
                result = 99;
            }

        } catch (Exception e) {
            result = 0;

        }


        try { //for petrol diesel
            String oilUrl = "http://www.petroldieselprice.com/Karnataka/petrol-diesel-kerosene-price-in-Bengaluru";
            org.jsoup.nodes.Document oilDoc = Jsoup.connect(oilUrl).get();
            Elements oilElem = oilDoc.select("tr.cart-subtotal");

            petrol = oilElem.first().children().get(1).text();
            diesel = oilElem.first().children().get(2).text();
            petrol = "P: " + petrol.replace(" Per Litre", "/L");
            diesel = "D: " + diesel.replace(" Per Litre", "/L");

        } catch (Exception e) {
            petrol = "- -";
            diesel = "- -";
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                gold22_textview.setText(carat22);
                gold24_textview.setText(carat24);
                petrol_textview.setText(petrol);
                diesel_textview.setText(diesel);

                if (result != 0) {
                    if (result <= 50) {
                        airNo_textview.setText(result + " AQI");
                        airQuality_textview.setText("Healthy");
                    } else if (result > 50 && result <= 100) {
                        airNo_textview.setText(result + " AQI");
                        airQuality_textview.setText("Good");
                    } else {
                        airNo_textview.setText(result + " AQI");
                        airQuality_textview.setText("Unhealthy");
                    }
                }
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return true;
    }


    private void fetchCard() {

      long cacheExpiration = 24*60*60; // 1 Day

        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        } else {}
                        displayPJ();
                        displayVV();
                        displayVK();
                        displayUV();
                        displayAN();
                        displayES();
                        displayDH();
                        displayHT();
                    }
                });
    }


    private void displayVK() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VK)) {
            vijayakarnataka.setVisibility(View.VISIBLE);
        } else {
            vijayakarnataka.setVisibility(View.GONE);
        }
    }

    private void displayPJ() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_PJ)) {
            prajavani.setVisibility(View.VISIBLE);
        } else {
            prajavani.setVisibility(View.GONE);
        }
    }

    private void displayVV() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VV)) {
            vijayavani.setVisibility(View.VISIBLE);
        } else {
            vijayavani.setVisibility(View.GONE);
        }
    }

    private void displayUV() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_UV)) {
            udayavani.setVisibility(View.VISIBLE);
        } else {
            udayavani.setVisibility(View.GONE);
        }
    }

    private void displayAN() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_AN)) {
            suvarna.setVisibility(View.VISIBLE);
        } else {
            suvarna.setVisibility(View.GONE);
        }
    }

    private void displayES() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_ES)) {
            esanje.setVisibility(View.VISIBLE);
        } else {
            esanje.setVisibility(View.GONE);
        }
    }

    private void displayDH() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_DH)) {
            deccanherald.setVisibility(View.VISIBLE);
        } else {
            deccanherald.setVisibility(View.GONE);
        }
    }

    private void displayHT() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_HT)) {
            hindustantimes.setVisibility(View.VISIBLE);
        } else {
            hindustantimes.setVisibility(View.GONE);
        }
    }




    @Override
    protected void onResume() {
        super.onResume();


        CardView overview_card = findViewById(R.id.overviewcard);
        FrameLayout locklayout = findViewById(R.id.locklayout);
        TextView locktxt = findViewById(R.id.locktext);
//        overview_card.setVisibility(View.GONE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Date firstlaunch = new Date((long) prefs.getLong("firstlaunch", ((long) System.currentTimeMillis() / 1000L)));
        Date currentDate = new Date(System.currentTimeMillis() / 1000L);
        int diffInDays = (int) ((currentDate.getTime() - firstlaunch.getTime()) / (60 * 60 * 24));
        Log.d("difference :", "" + diffInDays + ": " + currentDate.getTime() + ": " + firstlaunch.getTime());

        locktxt.setText("Kannada Kampu will be unlocked in "+ (3 - diffInDays ) + " days...");
        //  if more than 3 days & not unlocked, set unlock status
        if ((!prefs.getBoolean("isunlocked", false)) && diffInDays >= Constants.UNLOCK_DAYS) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isunlocked", true);
            OneSignal.sendTag("unlock_status", "unlocked");
            editor.apply();
        }

        //if unlocked enable or disable feature
        if (prefs.getBoolean("isunlocked", false)) {
            locklayout.setVisibility(View.GONE);
            overview_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Card view ", "Clicked");
                    Intent i = new Intent(MainScreen_Activity.this, ExclusiveActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
            Log.d("shared pref", "Feature unlocked");
        }
    }

}
