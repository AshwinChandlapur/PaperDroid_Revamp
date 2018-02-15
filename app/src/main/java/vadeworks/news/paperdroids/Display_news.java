package vadeworks.news.paperdroids;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.news.paperdroids.UdayaVaani.Udayavaani_Parser;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.news.paperdroids.VijayaVaani.Vijayavaani_Parser;
import vadeworks.paperdroid.R;

public class Display_news extends AppCompatActivity {

    TextView headlines_textview,content_textview,link_textview;
    ImageView imageView;

    String head,link,content,imgurl;
    String tag;


    News fullnews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_display);
        views_init();



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


        }

    }

    public void views_init(){

        headlines_textview = (TextView)findViewById(R.id.headline);
        content_textview = (TextView)findViewById(R.id.content);
        link_textview = (TextView)findViewById(R.id.link);
        imageView = (ImageView)findViewById(R.id.imageView);
    }

    public void display_news(final News fullnews){
        headlines_textview.setText(fullnews.head);
        content_textview.setText(fullnews.content);
        if(!fullnews.imgurl.isEmpty())
        {
            Picasso.with(getApplicationContext())
                    .load(fullnews.imgurl)
                    .placeholder(R.drawable.spaceullustration)
                    .error(R.drawable.spaceullustration)
                    .into(imageView);
        }else{
            Toast.makeText(getApplicationContext(),"Image Not Loading",Toast.LENGTH_LONG).show();
        }


        link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(fullnews.link));
                startActivity(i);
            }
        });

    }

    public News parse_content(News news){
        Document doc = new Document("");
        try {
             doc = Jsoup.connect(news.link).get();}catch (Exception e){}

            Elements image_url = doc.getElementsByClass("thumbImage").select("img");

            try {
                news.imgurl = "https://vijaykarnataka.indiatimes.com" + image_url.first().attr("src");
            } catch (Exception e) {
                Log.d("error", "error");
            }

            Elements body = doc.getElementsByTag("arttextxml");
            news.content = body.toString();
            news.content = Jsoup.parse(news.content).text();
            Log.d("parser","parser"+news.head);
            Log.d("parser","parser"+news.link);
            Log.d("parser","parser"+ news.content);
            Log.d("parser","parser"+news.imgurl);

        return news;
    }

}
