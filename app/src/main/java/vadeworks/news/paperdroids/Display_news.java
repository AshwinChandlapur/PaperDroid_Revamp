package vadeworks.news.paperdroids;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import vadeworks.news.paperdroids.Levels.PagerAdapter;
import vadeworks.news.paperdroids.Levels.Tab1;
import vadeworks.news.paperdroids.Levels.Tab2;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.paperdroid.R;

public class Display_news extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener {


    private final Bundle params = new Bundle();
    private TextView headlines_textview;
    private ImageView imageView;
    private String head;
    private String link;
    private String imgurl;
    private String docid;
    private TypingIndicatorView typingView;
    private FirebaseAnalytics mFirebaseAnalytics;

    private TypingIndicatorView typingIndicatorView;

    private FirebaseFirestore firestorenews;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_display);
        views_init();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Utils utils = new Utils(this);
        if (!utils.isConnected(getApplicationContext())) {
            utils.buildDialog(this).show();
        } else {
            Log.d("Internet Working", "Internet Working");
        }


        firestorenews = FirebaseFirestore.getInstance();

        docid = getIntent().getStringExtra("documentid");
        head = getIntent().getStringExtra("singleHead");
        link = getIntent().getStringExtra("singleLink");
        imgurl = getIntent().getStringExtra("singleImg");



        firestorenews.collection("NOTIFICATIONS").document(docid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                News todisplay = new News();
                if (task.isSuccessful()) {
                    todisplay = new News(head, link, imgurl);
                    todisplay.content = task.getResult().get("content").toString();
                    display_news(todisplay);
                }
                tabLayout = findViewById(R.id.displayNews_tabLayout);
                tabLayout.addTab(tabLayout.newTab().setText("Summary"));
                tabLayout.addTab(tabLayout.newTab().setText("Comphrehensive"));
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

                final ViewPager viewPager = findViewById(R.id.displayNews_pager);
                PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount(),todisplay.content);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                typingIndicatorView.setVisibility(View.GONE);
                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });

    }

    private void views_init() {

        headlines_textview = findViewById(R.id.headline);
        imageView = findViewById(R.id.imageView);
        typingIndicatorView = findViewById(R.id.loader_displayNews);
    }


    private void getScreenShot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
            imageView.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
            imageView.setDrawingCacheEnabled(false);


            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 20;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            shareImage(imageFile, head);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            Log.d("No Permission is Set", "No Permission is Set, Try after getting permission");
        }
    }


    private void shareImage(File file, String head) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        final String appPackageName = getPackageName();
//        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, head);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, head + "\n\nDownload News Duniya - Karnataka's Best Newspaper App\n" + "https://play.google.com/store/apps/details?id=" + appPackageName);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
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


    private void display_news(final News fullnews) {


        headlines_textview.setText(fullnews.head);

        if (!fullnews.imgurl.isEmpty()) {
            Picasso.with(getApplicationContext())
                    .load(fullnews.imgurl)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.backrepeat)
                    .into(imageView);
        } else {
            Toast.makeText(getApplicationContext(), "Could'nt Fetch the Image.", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFragmentManager().getBackStackEntryCount() > 0)
                getFragmentManager().popBackStackImmediate();
            Intent intent = new Intent(Display_news.this, MainScreen_Activity.class);
            finish();
            startActivity(intent);
        }
        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
