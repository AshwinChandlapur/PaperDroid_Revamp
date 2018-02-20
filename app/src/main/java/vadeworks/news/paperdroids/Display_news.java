package vadeworks.news.paperdroids;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_MainActivity;
import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.Esanje.Esanje_MainActivity;
import vadeworks.news.paperdroids.Esanje.Esanje_Parser;
import vadeworks.news.paperdroids.Prajavani.PrajaVaani_MainActivity;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.news.paperdroids.Splash_Screen.Splash_Main_Activity;
import vadeworks.news.paperdroids.UdayaVaani.UdayaVaani_MainActivity;
import vadeworks.news.paperdroids.UdayaVaani.Udayavaani_Parser;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_MainActivity;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.news.paperdroids.VijayaVaani.VijayaVaani_MainActivity;
import vadeworks.news.paperdroids.VijayaVaani.Vijayavaani_Parser;
import vadeworks.paperdroid.R;

public class Display_news extends AppCompatActivity {

    TextView headlines_textview,content_textview,link_textview;
    ImageView imageView;
    String head,link,content,imgurl;
    String tag;
    News fullnews;
    String notif= "";
    android.support.v7.widget.Toolbar toola;
    Boolean no_Internet = false, fail_News =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_display);
        views_init();
        if(!isConnected(this)) {
            buildDialog_noInternet(this).show();
            no_Internet =true;
        }
        else {
//            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show();
        }


        tag = getIntent().getStringExtra("tag");


        switch (tag){
            case "asianet":
                Log.d("Inside Asianet Swtich","inside");
                head= getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl= getIntent().getStringExtra("singleImg");
                fullnews = new News(head,link,imgurl);
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

            case "vijayakarnataka":
                Log.d("Inside Vijaya Swtich","inside");
                head= getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                fullnews = new News(head,link);
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

            case "udayavaani":
                Log.d("Inside Udaya Swtich","inside");
                head= getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                fullnews = new News(head,link);
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

            case "vijayavani":
                Log.d("Inside vv Swtich","inside");
                head= getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl= getIntent().getStringExtra("singleImg");
                fullnews = new News(head,link, imgurl);
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

            case "prajavani":
                Log.d("Inside pv Swtich","inside");
                head= getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl= getIntent().getStringExtra("singleImg");
                fullnews = new News(head,link, imgurl);
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

            case "esanje":
                Log.d("Inside es Swtich","inside");
                head= getIntent().getStringExtra("singleHead");
                link = getIntent().getStringExtra("singleLink");
                imgurl= getIntent().getStringExtra("singleImg");
                fullnews = new News(head,link, imgurl);
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

    public void views_init(){

        headlines_textview = findViewById(R.id.headline);
        content_textview = findViewById(R.id.content);
        link_textview = findViewById(R.id.link);
        imageView = findViewById(R.id.imageView);
        toola = (android.support.v7.widget.Toolbar) findViewById(R.id.toola);

        toola.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toola.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void display_news(final News fullnews){


        headlines_textview.setText(fullnews.head);
        if(!fullnews.content.isEmpty()){
            content_textview.setText(fullnews.content);
        }else{

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.news_fail,
                    (ViewGroup) findViewById(R.id.newsDisplay));

//            Toast toast = new Toast(getBaseContext());
//            toast.setView(view);
//            toast.setDuration(Toast.LENGTH_LONG);
//            toast.show();
            Toast.makeText(getApplicationContext(),"Could'nt Fetch the Content.",Toast.LENGTH_LONG).show();
        }

        if(!fullnews.imgurl.isEmpty())
        {
            Picasso.with(getApplicationContext())
                    .load(fullnews.imgurl)
                    .placeholder(R.drawable.image3)
                    .error(R.drawable.image3)
                    .into(imageView);
        }else{
            Toast.makeText(getApplicationContext(),"Could'nt Fetch the Image.",Toast.LENGTH_LONG).show();
        }


        link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headlines_textview.setVisibility(View.GONE);
                toola.setTitle(getApplicationContext().getResources().getString(R.string.app_name));

                LinearLayout linearLayout = findViewById(R.id.forAds);
                linearLayout.setVisibility(View.VISIBLE);
                WebView webView = (WebView)findViewById(R.id.webView);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(fullnews.link);

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent;
            if (notif.equals("notif")){
                switch (tag){
                    case "prajavani":
                        intent = new Intent(Display_news.this, PrajaVaani_MainActivity.class);
                        startActivity(intent);
                        break;
                    case "vijayavani":
                        intent = new Intent(Display_news.this, VijayaVaani_MainActivity.class);
                        startActivity(intent);
                        break;
                    case "vijayakarnataka":
                        intent = new Intent(Display_news.this, VijayaKarnataka_MainActivity.class);
                        startActivity(intent);
                        break;
                    case "udayavaani":
                        intent = new Intent(Display_news.this, UdayaVaani_MainActivity.class);
                        startActivity(intent);
                        break;
                    case "asianet":
                        intent = new Intent(Display_news.this, AsiaNet_MainActivity.class);
                        startActivity(intent);
                        break;
                    case "esanje":
                        intent = new Intent(Display_news.this, Esanje_MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        intent = new Intent(Display_news.this, PrajaVaani_MainActivity.class);
                        startActivity(intent);
                }

            }
            else {
                super.onBackPressed();
            }

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


    public AlertDialog.Builder buildDialog_noInternet(Context c) {

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


    public AlertDialog.Builder buildDialog_failNews(Context c)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater factory = LayoutInflater.from(c);
        final View view = factory.inflate(R.layout.news_fail, null);
        Button returnBack = view.findViewById(R.id.returnBack);
        returnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        builder.setView(view);
        return builder;


    }

}
