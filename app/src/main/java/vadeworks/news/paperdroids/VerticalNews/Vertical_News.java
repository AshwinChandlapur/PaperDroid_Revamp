package vadeworks.news.paperdroids.VerticalNews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Vertical_News extends AppCompatActivity {

    ArrayList<News> news = new ArrayList<News>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertical_news_activity);

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
                    }
                });

            }
        }).start();

    }

    private void initSwipePager(){
        VerticalViewPager verticalViewPager = (VerticalViewPager) findViewById(R.id.vPager);
        verticalViewPager.setAdapter(new VerticlePagerAdapter(this,news));
    }
}
