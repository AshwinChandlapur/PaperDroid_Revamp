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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.Date;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.paperdroid.BuildConfig;
import vadeworks.paperdroid.R;

public class MainScreen_Activity extends AppCompatActivity {


    private final Bundle params = new Bundle();
    String carat22, carat24, petrol, diesel;
    int result;
    private CardView prajavani;
    private CardView vijayavani;
    private CardView vijayakarnataka;
    private CardView udayavani;
    private CardView suvarna;
    private CardView esanje;
    private View parentLayout;
    private ImageView bottomImage;
    private TextView gold22_textview, gold24_textview, petrol_textview, diesel_textview, airNo_textview, airQuality_textview;
    private int click = 0;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String card_clicked;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private static final String CARD_VIEW_VISIBILITY="card_view_visibility";
    private static final String CARD_VIEW_TITLE ="card_view_title";
    private static final String CARD_VIEW_VISIBILITY_VK = "card_view_visibility_vk";
    private TextView mWelcomeTextView;
    private CardView mCardView;

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
        bottomImage = findViewById(R.id.bottomimage);


        gold22_textview = findViewById(R.id.gold22);
        gold24_textview = findViewById(R.id.gold24);
        petrol_textview = findViewById(R.id.petrol);
        diesel_textview = findViewById(R.id.diesel);
        airNo_textview = findViewById(R.id.airNo);
        airQuality_textview = findViewById(R.id.airQuality);


        mWelcomeTextView = findViewById(R.id.welcomeTextView);
        mCardView = findViewById(R.id.card_view);

        CardView overview_card = findViewById(R.id.overviewcard);
        overview_card.setVisibility(View.GONE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Date firstlaunch = new Date ((long)prefs.getLong("firstlaunch", ((long)System.currentTimeMillis() / 1000L)));
        Date currentDate = new Date(System.currentTimeMillis() / 1000L);
        int diffInDays = (int)( (currentDate.getTime() - firstlaunch.getTime())/ (60 * 60 * 24) );
        Log.d("diffrence :", ""+diffInDays + ": " + currentDate.getTime() + ": " + firstlaunch.getTime());
        //  if more than 3 days & not unlocked, set unlock status
        if ((!prefs.getBoolean("isunlocked", false) )&& diffInDays>= 3)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isunlocked", true);
            editor.apply();
        }
        //if unlocked enable or disable feature
        if (prefs.getBoolean("isunlocked", false)){
            overview_card.setVisibility(View.VISIBLE);
        }

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);


        fetchCard();




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


        bottomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = click + 1;

                if (click == 7) {

                    Snackbar.make(parentLayout, "Sherlock's Guess: You must be a Developer :D", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    try{
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("gold thread", "run: thread for gold");
                getRates();
            }
        }).start();
    }catch (Exception e){
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
        } catch (Exception e){
            carat22 = "- -";
            carat24="- -";
        }

        try{
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

        }catch (Exception e){
            result=0;

        }



        try{ //for petrol diesel

            String oilUrl = "http://www.petroldieselprice.com/Karnataka/petrol-diesel-kerosene-price-in-Bengaluru";

            org.jsoup.nodes.Document oilDoc = Jsoup.connect(oilUrl).get();
            Elements oilElem = oilDoc.select("tr.cart-subtotal");

            petrol = oilElem.first().children().get(1).text();
            diesel = oilElem.first().children().get(2).text();
            petrol = "P: "+petrol.replace(" Per Litre", "/L");
            diesel = "D: "+diesel.replace(" Per Litre", "/L");

        }catch (Exception e){
            petrol="- -";
            diesel="- -";
        }



        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                gold22_textview.setText(carat22);
                gold24_textview.setText(carat24);
                petrol_textview.setText(petrol);
                diesel_textview.setText(diesel);

                if(result!= 0){
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

        long cacheExpiration = 3600; // 1 hour in seconds.
        // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
        // retrieve values from the service.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }

        // [START fetch_config_with_callback]
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(MainScreen_Activity.this, "Fetch Succeeded",
//                                    Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
//                            Toast.makeText(MainScreen_Activity.this, "Fetch Failed",
//                                    Toast.LENGTH_SHORT).show();
                        }
                        displayCard();
                        displayVK();
                    }
                });
        // [END fetch_config_with_callback]
    }

    private void displayCard(){

        String cardTitle = mFirebaseRemoteConfig.getString(CARD_VIEW_TITLE);

        if(mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY)){
            mCardView.setVisibility(View.VISIBLE);
            mWelcomeTextView.setText(cardTitle);
        }else{
            mCardView.setVisibility(View.GONE);
            mWelcomeTextView.setVisibility(View.GONE);
        }
    }

    private void displayVK(){

        if(mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_VK)){
            vijayakarnataka.setVisibility(View.VISIBLE);
        }else{
            vijayakarnataka.setVisibility(View.GONE);
        }
    }


}
