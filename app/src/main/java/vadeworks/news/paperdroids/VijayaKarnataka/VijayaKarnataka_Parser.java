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





    public News Parse_For_Content(final News news) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Document doc = Jsoup.connect(news.link).get();
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


                } catch (IOException e) {

                }
            }
        }).start();

        Log.d("timestamp","timestamp of Method");
        return news;
    }


    public News Parser_Sidenews(final News news){

        return news;
    }




}
