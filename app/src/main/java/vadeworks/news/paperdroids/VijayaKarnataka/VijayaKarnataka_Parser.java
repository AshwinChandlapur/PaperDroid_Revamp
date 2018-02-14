package vadeworks.news.paperdroids.VijayaKarnataka;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class VijayaKarnataka_Parser implements Paper {

    String vijayakarnataka_base_url = "http://kannada.asianetnews.com/";
    String category_url;
    Document vijayakarnataka_doc;
    Elements vijayakarnataka_elem;
    ArrayList<News> news = new ArrayList<News>();


    @Override
    public ArrayList<News> parseHeadLines() {
        return null;
    }

    @Override
    public News parseNewsPost(News news) {

        try {
            vijayakarnataka_doc = Jsoup.connect(news.link).get();
        }catch (Exception e){

        }
            vijayakarnataka_elem = vijayakarnataka_doc.getElementsByClass("thumbImage").select("img");

            try {
                news.imgurl = "https://vijaykarnataka.indiatimes.com" + vijayakarnataka_elem.first().attr("src");
            } catch (Exception e) {
                Log.d("error", "error");
            }

            vijayakarnataka_elem = vijayakarnataka_doc.getElementsByTag("arttextxml");
            news.content = vijayakarnataka_elem.toString();
            news.content = Jsoup.parse(news.content).text();
            Log.d("parser","parser"+news.head);
            Log.d("parser","parser"+news.link);
            Log.d("parser","parser"+ news.content);
            Log.d("parser","parser"+news.imgurl);

        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {
        return null;
    }

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

}
