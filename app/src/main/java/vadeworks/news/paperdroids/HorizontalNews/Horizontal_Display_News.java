package vadeworks.news.paperdroids.HorizontalNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Horizontal_Display_News extends AppCompatActivity {

    ViewPager mViewPager;
    private ArrayList<News> news = new ArrayList<>();
    private int position;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_display_news);

        Intent i = getIntent();
        if (i != null) {
            news = (ArrayList<News>) i.getSerializableExtra("newsObject");
            position = i.getIntExtra("position", 0);
            tag = i.getStringExtra("tag");
        }

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this, news, position, tag);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(5);
    }
}
