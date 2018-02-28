package vadeworks.news.paperdroids.MainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
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


        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("gold thread", "run: thread for gold");
                getRates();
            }
        }).start();


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


            //for petrol diesel

            String oilUrl = "http://www.petroldieselprice.com/Karnataka/petrol-diesel-kerosene-price-in-Bengaluru";

            org.jsoup.nodes.Document oilDoc = Jsoup.connect(oilUrl).get();
            Elements oilElem = oilDoc.select("tr.cart-subtotal");

            petrol = oilElem.first().children().get(1).text();
            diesel = oilElem.first().children().get(2).text();
            petrol = petrol.replace(" Per Litre", "/L");
            diesel = diesel.replace(" Per Litre", "/L");


        } catch (Exception e) {
        }


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gold22_textview.setText(carat22);
                gold24_textview.setText(carat24);
                petrol_textview.setText(petrol);
                diesel_textview.setText(diesel);
                airNo_textview.setText(result + " AQI");
                if (result <= 50) {
                    airQuality_textview.setText("Healthy");
                } else if (result > 50 && result <= 100) {
                    airQuality_textview.setText("Good");
                } else {
                    airQuality_textview.setText("Unhealthy");
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


}
