package vadeworks.news.paperdroids;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nikhil on 12/02/18.
 */

public class VijayaKarnatakaa implements Paper {

    String baseurl = "https://vijaykarnataka.indiatimes.com/";

    @Override
    public ArrayList<News> parseHeadLines() {
        ArrayList<News> news = new ArrayList<>();

                try {
                    Elements vijayakarnataka_headlines_elem; //this has the headline
                    Document vijayakarnataka_doc = Jsoup.connect(baseurl).get();//this is of type Document
                    vijayakarnataka_headlines_elem = vijayakarnataka_doc.getElementsByClass("other_main_news1").select("ul").select("li").select("a");
                    //vijayakarnataka_headlines_elem is of type Elements

                    int i;
                    for(i=0;i<vijayakarnataka_headlines_elem.size();i++){
                        String link = baseurl+vijayakarnataka_headlines_elem.get(i).attr("href");
                        String headline = vijayakarnataka_headlines_elem.get(i).text();
                        news.add(new News(headline,link));
                        news.get(i).showNews("fromVK");
                    }
                }
                catch(Exception e) {
//                    Log.e("exception", e.toString());
                }
        news.get(0).showNews("outside");
        return news;
    }

    @Override
    public News parseNewsPost(News news) {
        return null;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {



        return null;
    }

    public String getCategoryUrl(String tag){
        switch (tag){
            case "sports": return "";
            default: return "";
        }
    }

}
