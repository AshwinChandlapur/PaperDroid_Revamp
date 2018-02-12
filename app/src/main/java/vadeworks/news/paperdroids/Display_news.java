package vadeworks.news.paperdroids;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import vadeworks.paperdroid.R;

public class Display_news extends ActionBarActivity {



    TextView headlines_textview,content_textview,link_textview;
    ImageView imageView;

    String head,link,content,imgurl;


    News single;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_news_activity);
        views_init();


//        android.support.v7.app.ActionBar AB = getSupportActionBar();
//        AB.hide();



        head = getIntent().getStringExtra("singleHead");
        link = getIntent().getStringExtra("singleLink");
        single = new News(head, link);


        Log.d("timestamp","timestamp News star");
        new Thread(new Runnable() {
            @Override
            public void run() {
                single=parse_content(single);
                Log.d("timestamp","timestamp News Ret");
                imgurl = single.imgurl;
                content = single.content;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headlines_textview.setText(head);
                        content_textview.setText(content);
                        if(!imgurl.isEmpty())
                        {
                            Picasso.with(getApplicationContext())
                                    .load(imgurl)
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
                                i.setData(Uri.parse(link));
                                startActivity(i);
                            }
                        });
                        Log.d("timestamp","timestamp Done");
                    }
                });


            }
        }).start();
        Log.d("timestamp","timestamp Done and Dusted");






//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//
//                    Document doc = Jsoup.connect(link).get();
//                    Elements article = doc.getElementsByClass("article-wrap new-article-desc").select("p");
//                    content = article.toString();
//                    content = Jsoup.parse(content).text();
//
//                    Log.d("parser","parser"+head);
//                    Log.d("parser","parser"+link);
//                    Log.d("parser","parser"+imgurl);
//                    Log.d("parser","parser"+ content);
//
//
//
//                } catch (IOException e) {
//
//                }
//            }
//        }).start();






    }

    public void views_init(){

        headlines_textview = (TextView)findViewById(R.id.headline);
        content_textview = (TextView)findViewById(R.id.content);
        link_textview = (TextView)findViewById(R.id.link);
        imageView = (ImageView)findViewById(R.id.imageView);
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


//        } catch (IOException e) {
//
//        }



        return news;
    }

}
