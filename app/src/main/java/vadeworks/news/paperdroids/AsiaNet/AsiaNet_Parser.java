package vadeworks.news.paperdroids.AsiaNet;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import vadeworks.news.paperdroids.News;

/**
 * Created by ashwinchandlapur on 12/02/18.
 */

public class AsiaNet_Parser {




    public News Parse_For_Content(final News news){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Document doc = Jsoup.connect(news.link).get();
                    Elements article = doc.getElementsByClass("article-wrap new-article-desc").select("p");
                    news.content = article.toString();
                    news.content = Jsoup.parse(news.content).text();

                    Log.d("parser","parser"+news.head);
                    Log.d("parser","parser"+news.link);
                    Log.d("parser","parser"+news.imgurl);
                    Log.d("parser","parser"+ news.content);



                } catch (IOException e) {

                }
            }
        }).start();



        return news;
    }
}
