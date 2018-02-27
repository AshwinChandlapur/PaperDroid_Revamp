package vadeworks.news.paperdroids.VerticalNews;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Vertical_News extends AppCompatActivity {

    private ArrayList<News> news = new ArrayList<>();
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
        verticalLink=  getIntent().getStringExtra("verticalLink");


        if (!isConnected(this)) {
            buildDialog(this).show();

        } else {
            Log.d("Internet Working", "Internet Working");
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }

        //showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                CuratedNews_Parser parser = new CuratedNews_Parser();
                news = parser.parseTop10(verticalLink);
                Log.d("Verticle News Size is", "Vertical News size" + news.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initSwipePager();

                        Snackbar snack = Snackbar.make(parentLayout, "Swipe up for Top 10", Snackbar.LENGTH_LONG);
                        View view = snack.getView();
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                        params.gravity = Gravity.TOP;
                        view.setLayoutParams(params);
                        snack.show();

//                        Snackbar.make(parentLayout,"Swipe up for Top 10",Snackbar.LENGTH_INDEFINITE).show();
                        typingView.setVisibility(View.GONE);
                    }
                });
            }
        }).start();


    }

    private void initSwipePager() {
        VerticalViewPager verticalViewPager = findViewById(R.id.vPager);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this, news));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Vertical_News.this, MainScreen_Activity.class);
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
