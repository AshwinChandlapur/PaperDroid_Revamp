package vadeworks.news.paperdroids.VerticalNews;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;



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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_news_activity);
        typingView = findViewById(R.id.loadera);

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





}
