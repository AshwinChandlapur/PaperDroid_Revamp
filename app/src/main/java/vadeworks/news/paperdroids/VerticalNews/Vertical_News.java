package vadeworks.news.paperdroids.VerticalNews;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.paperdroid.R;

public class Vertical_News extends AppCompatActivity {

    ArrayList<News> news = new ArrayList<News>();
    private TypingIndicatorView typingView;
    View parentLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_news_activity);
        parentLayout = findViewById(android.R.id.content);
        typingView = findViewById(R.id.loadera);



        if(!isConnected(this)) {
            buildDialog(this).show();

        }
        else {
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }

        //showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                TopNews_Parser parser = new TopNews_Parser();
                news = parser.parseHeadLines();
                Log.d("Verticle News Size is","Vertical News size"+news.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initSwipePager();
                        Snackbar.make(parentLayout,"Swipe up for Top 10",Snackbar.LENGTH_LONG).show();
                        typingView.setVisibility(View.GONE);
                    }
                });
                //hideProgress();
            }
        }).start();


    }

    private void initSwipePager(){
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vPager);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this,news));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(Vertical_News.this, PrajaVaani_MainActivity.class);
            startActivity(intent);
        }
        return true;
    }



    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null &&  netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

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
                intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
                startActivity(intent);
            }
        });


        builder.setView(view);
        return builder;
    }






}
