package vadeworks.news.paperdroids.HorizontalNews;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;

public class Horizontal_Display_News extends AppCompatActivity {

    ViewPager mViewPager;
    private ArrayList<News> news = new ArrayList<>();
    private int position;

    private FloatingActionButton share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.horizontal_display_news);

        askPermission();
        Intent i = getIntent();
        if (i != null) {
            news = (ArrayList<News>) i.getSerializableExtra("newsObject");
            position = i.getIntExtra("position", 0);
        }

        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(this, news, position);
        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(position);
        mViewPager.setOffscreenPageLimit(5);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied!.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void askPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        }
    }


}
