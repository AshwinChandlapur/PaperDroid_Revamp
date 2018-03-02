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
import android.support.design.widget.FloatingActionButton;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Esanje.Esanje_Parser;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.Udayavaani_Parser;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.Vijayavaani_Parser;
import vadeworks.paperdroid.R;

public class Display_news extends AppCompatActivity {

    private final String notif = "";
    private final Bundle params = new Bundle();
    private TextView headlines_textview;
    private TextView content_textview;
    private TextView link_textview;
    private ImageView imageView;
    private String head;
    private String link;
    private String imgurl;
    private String tag;
    private News fullnews;
    private android.support.v7.widget.Toolbar toola;
    private TypingIndicatorView typingView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String news_display_previous_activity;
    private FloatingActionButton share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_display);
        views_init();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (!isConnected(this)) {
            buildDialog_noInternet(this).show();

        } else {
            Log.d("Internet Working", "Internet Working");
        }


        tag = getIntent().getStringExtra("tag");
        news_display_previous_activity = "DisplayNews_Previous_Is_" + tag;
        mFirebaseAnalytics.logEvent(news_display_previous_activity, params);


        switch (tag) {
            case Constants.asianet:
                Log.d("Inside Asianet Swtich", "inside");
                head = getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl = getIntent().getStringExtra("singleImg");
                fullnews = new News(head, link, imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AsiaNet_Parser parser = new AsiaNet_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews);
                            }
                        });
                    }
                }).start();

                break;

            case Constants.vijayakarnataka:
                Log.d("Inside Vijaya Swtich", "inside");
                head = getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                fullnews = new News(head, link);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews);
                            }
                        });
                    }
                }).start();
                break;

            case Constants.udayavani:
                Log.d("Inside Udaya Swtich", "inside");
                head = getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                fullnews = new News(head, link);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Udayavaani_Parser parser = new Udayavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews);
                            }
                        });
                    }
                }).start();
                break;

            case Constants.vijayavani:
                Log.d("Inside vv Swtich", "inside");
                head = getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl = getIntent().getStringExtra("singleImg");
                fullnews = new News(head, link, imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Vijayavaani_Parser parser = new Vijayavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews);
                            }
                        });
                    }
                }).start();
                break;

            case Constants.prajavani:
                Log.d("Inside pv Swtich", "inside");
                head = getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl = getIntent().getStringExtra("singleImg");
                fullnews = new News(head, link, imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Prajavaani_Parser parser = new Prajavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews);
                            }
                        });
                    }
                }).start();
                break;

            case Constants.esanje:
                Log.d("Inside es Swtich", "inside");
                head = getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl = getIntent().getStringExtra("singleImg");
                fullnews = new News(head, link, imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Esanje_Parser parser = new Esanje_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews);
                            }
                        });
                    }
                }).start();
                break;
        }

    }

    private void views_init() {

        final String shareAnalytics = "ShareNews";
        headlines_textview = findViewById(R.id.headline);
        content_textview = findViewById(R.id.content);
        link_textview = findViewById(R.id.link);
        imageView = findViewById(R.id.imageView);
        typingView = findViewById(R.id.loader);
        share = findViewById(R.id.share);
        toola = findViewById(R.id.toola);
        toola.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toola.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
                }
                mFirebaseAnalytics.logEvent(shareAnalytics, params);


                    getScreenShot();



            }
        });

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
            Log.d("No Permission is Set","No Permission is Set, Try after getting permission");
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
        if (!fullnews.content.isEmpty()) {
            content_textview.setText(fullnews.content);
        } else {

            Toast.makeText(getApplicationContext(), "Could'nt Fetch the Content.", Toast.LENGTH_LONG).show();
        }

        if (!fullnews.imgurl.isEmpty()) {
            Picasso.with(getApplicationContext())
                    .load(fullnews.imgurl)
                    .placeholder(R.drawable.image3)
                    .error(R.drawable.image3)
                    .into(imageView);
        } else {
            Toast.makeText(getApplicationContext(), "Could'nt Fetch the Image.", Toast.LENGTH_LONG).show();
        }


        link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headlines_textview.setVisibility(View.GONE);
                toola.setTitle(getApplicationContext().getResources().getString(R.string.app_name));

                LinearLayout linearLayout = findViewById(R.id.forAds);
                linearLayout.setVisibility(View.VISIBLE);
                WebView webView = findViewById(R.id.webView);
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(fullnews.link);

            }
        });
        typingView.setVisibility(View.GONE);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent;
            if (notif.equals("notif")) {
                switch (tag) {
                    case Constants.prajavani:
                        intent = new Intent(Display_news.this, PrajaVaani_MainActivity.class);
                        startActivity(intent);
                        break;
                    case Constants.vijayavani:
                        intent = new Intent(Display_news.this, VijayaVaani_MainActivity.class);
                        startActivity(intent);
                        break;
                    case Constants.vijayakarnataka:
                        intent = new Intent(Display_news.this, VijayaKarnataka_MainActivity.class);
                        startActivity(intent);
                        break;
                    case Constants.udayavani:
                        intent = new Intent(Display_news.this, UdayaVaani_MainActivity.class);
                        startActivity(intent);
                        break;
                    case Constants.asianet:
                        intent = new Intent(Display_news.this, AsiaNet_MainActivity.class);
                        startActivity(intent);
                        break;
                    case Constants.esanje:
                        intent = new Intent(Display_news.this, Esanje_MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        intent = new Intent(Display_news.this, PrajaVaani_MainActivity.class);
                        startActivity(intent);
                }

            } else {
                super.onBackPressed();
            }

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


    private AlertDialog.Builder buildDialog_noInternet(Context c) {

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
