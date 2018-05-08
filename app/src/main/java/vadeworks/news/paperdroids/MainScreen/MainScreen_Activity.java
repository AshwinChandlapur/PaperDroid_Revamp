package vadeworks.news.paperdroids.MainScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.Cricbuzz;
import vadeworks.news.paperdroids.DNA.Dna_Activity;
import vadeworks.news.paperdroids.DeccanHerald.DeccanHerald_Activity;
import vadeworks.news.paperdroids.Exclusive.ExclusiveActivity;
import vadeworks.news.paperdroids.HindustanTimes.HindustanTimes_Activity;
import vadeworks.news.paperdroids.IndianExpress.IndianExpress_Activity;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Special_Card;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.Utils;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.paperdroid.BuildConfig;
import vadeworks.paperdroid.R;

public class MainScreen_Activity extends AppCompatActivity {


    private static final String CARD_VIEW_VISIBILITY_VK = "card_view_visibility_vk";
    private static final String CARD_VIEW_VISIBILITY_PJ = "card_view_visibility_pj";
    private static final String CARD_VIEW_VISIBILITY_VV = "card_view_visibility_vv";
    private static final String CARD_VIEW_VISIBILITY_UV = "card_view_visibility_uv";

    private static final String CARD_VIEW_VISIBILITY_HT = "card_view_visibility_ht";
    private static final String CARD_VIEW_VISIBILITY_DH = "card_view_visibility_dh";
    private static final String CARD_VIEW_VISIBILITY_IE = "card_view_visibility_ie";
    private static final String CARD_VIEW_VISIBILITY_DNA = "card_view_visibility_dna";


    private static int match_id;
    private final Bundle params = new Bundle();
    @BindView(R.id.specialCards)
    CardView ipl_parent;
    @BindView(R.id.mchDesc)
    TextView mchDesc;
    @BindView(R.id.mchStatus)
    TextView mchStatus;
    @BindView(R.id.battingTeamImage)
    ImageView battingTeamImage;
    @BindView(R.id.battingTeamText)
    TextView battingTeamText;
    @BindView(R.id.scoreCard)
    TextView scoreCard;
    @BindView(R.id.cricketImage)
    ImageView cricketImage;
    @BindView(R.id.loader)
    TypingIndicatorView typingView;
    @BindView(R.id.prajavani)
    CardView prajavani;
    @BindView(R.id.vijayavani)
    CardView vijayavani;
    @BindView(R.id.vijayakarnataka)
    CardView vijayakarnataka;
    @BindView(R.id.udayavani)
    CardView udayavani;
    @BindView(R.id.deccanherald)
    CardView deccanherald;
    @BindView(R.id.hindustantimes)
    CardView hindustantimes;
    @BindView(R.id.indianExpress)
    CardView indianExpress;
    @BindView(R.id.dna)
    CardView dna;
    @BindView(R.id.bottomimage)
    ImageView bottomImage;
    @BindView(R.id.exclusive_background)
    ImageView exclusive_background_image;
    @BindView(R.id.gold22)
    TextView gold22_textview;
    @BindView(R.id.gold24)
    TextView gold24_textview;
    @BindView(R.id.petrol)
    TextView petrol_textview;
    @BindView(R.id.diesel)
    TextView diesel_textview;
    @BindView(R.id.airNo)
    TextView airNo_textview;
    @BindView(R.id.airQuality)
    TextView airQuality_textview;
    private FirebaseFirestore firestoreNews;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private String carat22, carat24, petrol, diesel;
    private int result;
    private String card_clicked;
    private View parentLayout;
    private int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen_activity);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        parentLayout = findViewById(android.R.id.content);
        parentLayout.setFocusableInTouchMode(true);
        parentLayout.requestFocus();
        parentLayout.setFocusableInTouchMode(false);


        Picasso.with(this).load(R.drawable.kannadas).placeholder(R.drawable.kannadas).error(R.drawable.kannadas).into(exclusive_background_image);

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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Date firstlaunch = new Date(prefs.getLong("firstlaunch", (System.currentTimeMillis() / 1000L)));
        Date currentDate = new Date(System.currentTimeMillis() / 1000L);
        int diffInDays = (int) ((currentDate.getTime() - firstlaunch.getTime()) / (60 * 60 * 24));
        Log.d("difference :", "" + diffInDays + ": " + currentDate.getTime() + ": " + firstlaunch.getTime());

        locktxt.setText(Constants.unlock + (Constants.UNLOCK_DAYS - diffInDays) + " days...");
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

//        String manufacturer = "xiaomi";
//        if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
//            //this will open auto start screen where user can enable permission for your app
//            Intent intent1 = new Intent();
//            intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
//            startActivity(intent1);
//        }


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


        deccanherald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_dh);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, DeccanHerald_Activity.class);
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

        indianExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_ie);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, IndianExpress_Activity.class);
                startActivity(i);
            }
        });

        dna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_clicked = getResources().getString(R.string.toolbar_title_home_dna);
                mFirebaseAnalytics.logEvent(card_clicked, params);
                Intent i = new Intent(MainScreen_Activity.this, Dna_Activity.class);
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

        Utils utils = new Utils(this);
        if (utils.isConnected(getApplicationContext())) {

            initializeNewViews();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("Test", "Inside 2500 Runnable");
                    refreshScores(true);
                }
            }, 2500);


            AppRate.with(this)
                    .setInstallDays(0) // default 10, 0 means install day.
                    .setLaunchTimes(4) // default 10
                    .setRemindInterval(2) // default 1
                    .setShowLaterButton(true) // default true
                    .setShowNeverButton(false)
                    .setCancelable(false)
                    .setDebug(false) // default false
                    .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                        @Override
                        public void onClickButton(int which) {
                            Log.d("OKOK"+MainScreen_Activity.class.getName(), Integer.toString(which));

                        }
                    })
                    .monitor();

            // Show a dialog if meets conditions
            AppRate.showRateDialogIfMeetsConditions(this);

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

            petrol = oilElem.first().children().get(2).text();
            diesel = oilElem.first().children().get(4).text();
            petrol = "P: " + petrol.replace(" Litre", "L");
            diesel = "D: " + diesel.replace(" Litre", "L");

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

                        displayDH();
                        displayHT();
                        displayIE();
                        displayDNA();
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

    private void displayIE() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_IE)) {
            indianExpress.setVisibility(View.VISIBLE);
        } else {
            indianExpress.setVisibility(View.GONE);
        }
    }

    private void displayDNA() {

        if (mFirebaseRemoteConfig.getBoolean(CARD_VIEW_VISIBILITY_DNA)) {
            dna.setVisibility(View.VISIBLE);
        } else {
            dna.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        CardView overview_card = findViewById(R.id.overviewcard);
        FrameLayout locklayout = findViewById(R.id.locklayout);
        TextView locktxt = findViewById(R.id.locktext);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Date firstlaunch = new Date(prefs.getLong("firstlaunch", (System.currentTimeMillis() / 1000L)));
        Date currentDate = new Date(System.currentTimeMillis() / 1000L);
        int diffInDays = (int) ((currentDate.getTime() - firstlaunch.getTime()) / (60 * 60 * 24));
        Log.d("difference :", "" + diffInDays + ": " + currentDate.getTime() + ": " + firstlaunch.getTime());

        locktxt.setText(Constants.unlock + (Constants.UNLOCK_DAYS - diffInDays) + " days...");
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

    private void initializeNewViews() {
        Log.d("Test", "InitializeNewViews");
        firestoreNews = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestoreNews.collection("ipl").document("match_ids");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {

                        match_id = Integer.parseInt(document.get("match_ids").toString());
                        Log.d("DocumentSnapshot data", "DocumentSnapshot data: " + match_id);
                        Log.d("DocumentSnapshot data", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("DocumentSnapshot", "No such document");
                    }
                } else {
                    Log.d("DocumentSnapshot", "get failed with ", task.getException());
                }
            }
        });

//        ipl_parent.setVisibility(View.GONE);
        Log.d("Test", "InitializeNewViews Done");



    }

    private void refreshScores(boolean auto_refresh_) {
        if (auto_refresh_) {
            Log.d("DocumentSnapshot data", "DocumentSnapshot datas: " + match_id);
            if (match_id != 0) {
                ipl_parent.setVisibility(View.VISIBLE);
                ipl_parent.setOnClickListener(null);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Cricbuzz cricbuzz = new Cricbuzz();
                            Map<String, Map> score = cricbuzz.livescore(match_id + "");
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            String data = gson.toJson(score);

                            JSONObject _data = new JSONObject(data);
                            Log.d("data", data);

                            JSONObject matchinfo = _data.getJSONObject("matchinfo");
                            Log.d("matchinfo", matchinfo.toString());
                            final String match = matchinfo.get("mchdesc").toString();
                            final String status = matchinfo.get("status").toString();


                            JSONObject batting = _data.getJSONObject("batting");
                            JSONArray team = batting.getJSONArray("team");
                            JSONObject _team = team.getJSONObject(0);
                            final String battingteam = _team.getString("team");

                            JSONArray _score = batting.getJSONArray("score");
                            Log.d("_score", _score.toString());
                            JSONObject __score = _score.getJSONObject(0);
                            final String runs = __score.getString("runs");
                            final String wickets = __score.getString("wickets");
                            final String overs = __score.getString("overs");


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    typingView.setVisibility(View.GONE);

                                    mchDesc.setText(match);
                                    mchStatus.setText(status);
                                    battingTeamText.setText(battingteam);
                                    String ignoreCase = battingteam.toLowerCase();
                                    switch (ignoreCase) {
                                        case "csk":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.chennai));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "kkr":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.kolkatta));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "rcb":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.bangalore));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "rr":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.rajasthan));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "dd":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.dehli));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "srh":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.hyderabad));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "mi":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.mumbai));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "kxip":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.punjab));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;

                                        case "ind":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.ind));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "aus":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.aus));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "ban":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.bangladesh));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "eng":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.eng));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "nz":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.nz));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "pak":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.pakistan));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "rsa":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.sf));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "sl":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.sl));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        case "wi":
                                            battingTeamImage.setImageDrawable(getResources().getDrawable(R.drawable.wi));
                                            battingTeamText.setVisibility(View.GONE);
                                            break;
                                        default:
                                            battingTeamImage.setVisibility(View.GONE);
                                            battingTeamText.setVisibility(View.VISIBLE);
                                            break;
                                    }
                                    scoreCard.setText(runs + "-" + wickets + " (" + overs + ")");

                                    ipl_parent.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(MainScreen_Activity.this, Special_Card.class);
                                            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainScreen_Activity.this, findViewById(R.id.specialCards), "cardSpecial");
                                            startActivity(intent, optionsCompat.toBundle());
                                        }
                                    });
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshScores(true);
                    }
                }, 10000);
            }
        }
    }


}







