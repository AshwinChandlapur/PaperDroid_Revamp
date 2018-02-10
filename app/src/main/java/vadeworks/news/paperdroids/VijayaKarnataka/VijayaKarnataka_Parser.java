package vadeworks.news.paperdroids.VijayaKarnataka;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import vadeworks.news.paperdroids.News;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class VijayaKarnataka_Parser {

    public News Parse_Headlines(final News news) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Document doc = Jsoup.connect(news.link).get();
                    Elements image_url = doc.getElementsByClass("thumbImage").select("img");

                    try {
                        news.imgurl = "https://vijaykarnataka.indiatimes.com" + image_url.select("img").first().attr("src");
                    } catch (Exception e) {
                        Log.d("error", "error");
                    }

                    Elements body = doc.getElementsByTag("arttextxml");
                    news.content = body.toString();
                    news.content = Jsoup.parse(news.content).text();
                    Log.d("parser",news.head);
                    Log.d("parser",news.link);
                    Log.d("parser", news.content);
                    Log.d("parser",news.imgurl);


                } catch (IOException e) {

                }
            }
        }).start();

        return news;
    }


    public News Parser_Sidenews(final News news){

        return news;
    }




}
