package vadeworks.news.paperdroids.VerticalNews;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Vertical_News extends AppCompatActivity {

    FirebaseFirestore firestoreNews;
    private ArrayList<News> newsList = new ArrayList<>();
    private TypingIndicatorView typingView;
    private View parentLayout;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String card_clicked;
    private Bundle params = new Bundle();
    private String verticalLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_news_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        parentLayout = findViewById(android.R.id.content);
        typingView = findViewById(R.id.loadera);
        card_clicked = getResources().getString(R.string.toolbar_title_home_top10);
        mFirebaseAnalytics.logEvent(card_clicked, params);
        verticalLink = getIntent().getStringExtra("verticalLink");

        firestoreNews = FirebaseFirestore.getInstance();


        if (!isConnected(this)) {
            buildDialog(this).show();

        } else {
            Log.d("Internet Working", "Internet Working");
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }

        Log.d("Starting Fetch", "Starting Fetch");
        firestoreNews.collection("TOP_10")
                .orderBy("imgurl", Query.Direction.DESCENDING).limit(Constants.TOP_10_LIMIT)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Log.d("Docu", documentSnapshot.getId() + " => " + documentSnapshot.getData());

                                Log.d("AllContent", "all" + documentSnapshot.get("content"));
                                News news = documentSnapshot.toObject(News.class);

                                newsList.add(news);
                                Snackbar.make(parentLayout, "Swipe Up to read more...", Snackbar.LENGTH_SHORT).show();
                                initSwipePager();
                            }
                            Log.d("Starting Fetch", "Finishing Fetch");
                        } else {
                            Log.w("Docu", "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    private void initSwipePager() {
        VerticalViewPager verticalViewPager = findViewById(R.id.vPager);
        verticalViewPager.setOffscreenPageLimit(5);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this, newsList));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Vertical_News.this, MainScreen_Activity.class);
            finish();
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
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });


        Button data = view.findViewById(R.id.switchData);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);
            }
        });


        builder.setView(view);
        return builder;
    }



}
