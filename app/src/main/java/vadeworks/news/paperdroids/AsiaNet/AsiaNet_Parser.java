package vadeworks.news.paperdroids.AsiaNet;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;

/**
 * Created by ashwinchandlapur on 12/02/18.
 */

public class AsiaNet_Parser implements Paper {


    String asianet_base_url = "http://kannada.asianetnews.com/";
    Document asianet_doc;
    ArrayList<News> news = new ArrayList<News>();

    @Override
    public ArrayList<News> parseHeadLines() {

        try{
            asianet_doc = Jsoup.connect(asianet_base_url).get();//this is of type Document
        }catch (Exception e){}

        Log.d("timestamp","timestamp Headlines Done");
        Elements asianet_headlines_elem = asianet_doc.getElementsByClass("col-sm-4 col-xs-6 cl-text-bg").select("a");

        int i;
        for(i=0; i< asianet_headlines_elem.size(); i++){

            String link = asianet_headlines_elem.get(i).attr("href");
            link = "http://kannada.asianetnews.com"+link;
            String headline = asianet_headlines_elem.get(i).select("img:lt(1)").attr("title");
            String img_url = asianet_headlines_elem.get(i).select("img:lt(1)").attr("data-original");
            //lt(n) --->elements whose sibling index is less than n

            news.add(new News(headline,link,img_url));
            news.get(i).showNews();
        }

        return news;
    }

    @Override
    public News parseNewsPost(News news) {


        try {

            Document doc = Jsoup.connect(news.link).get();
            Elements article = doc.getElementsByClass("article-wrap new-article-desc").select("p");
            news.content = article.toString();
            news.content = Jsoup.parse(news.content).text();

            Log.d("parser","Asianet Headline parser"+news.head);
            Log.d("parser","Asianet Link parser"+news.link);
            Log.d("parser","Asianet ImgUrl parser"+news.imgurl);
            Log.d("parser","Asianet Content parser"+ news.content);



        } catch (IOException e) {

        }

        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {
        return null;
    }



//    public News Parse_For_Content(final News news){
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//
//                    Document doc = Jsoup.connect(news.link).get();
//                    Elements article = doc.getElementsByClass("article-wrap new-article-desc").select("p");
//                    news.content = article.toString();
//                    news.content = Jsoup.parse(news.content).text();
//
//                    Log.d("parser","parser"+news.head);
//                    Log.d("parser","parser"+news.link);
//                    Log.d("parser","parser"+news.imgurl);
//                    Log.d("parser","parser"+ news.content);
//
//
//
//                } catch (IOException e) {
//
//                }
//            }
//        }).start();
//
//
//
//        return news;
//    }
}
