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
    String category_url;
    Document asianet_doc;
    Elements asianet_elem;
    ArrayList<News> news = new ArrayList<News>();

    @Override
    public ArrayList<News> parseHeadLines() {

        try{
            asianet_doc = Jsoup.connect(asianet_base_url).get();//this is of type Document
            Log.d("timestamp","timestamp Headlines Done");
            asianet_elem = asianet_doc.getElementsByClass("col-sm-4 col-xs-6 cl-text-bg").select("a");
            int i;
            for(i=0; i< asianet_elem.size(); i++){

                String link = asianet_elem.get(i).attr("href");
                link = "http://kannada.asianetnews.com"+link;
                String headline = asianet_elem.get(i).select("img:lt(1)").attr("title");
                String img_url = asianet_elem.get(i).select("img:lt(1)").attr("data-original");
                //lt(n) --->elements whose sibling index is less than n

                news.add(new News(headline,link,img_url));
                news.get(i).showNews();
            }

        }catch (Exception e){}
        return news;
    }

    @Override
    public News parseNewsPost(News news) {

        try {
            asianet_doc = Jsoup.connect(news.link).get();
            asianet_elem = asianet_doc.getElementsByClass("article-wrap new-article-desc").select("p");
            news.content = asianet_elem.toString();
            news.content = Jsoup.parse(news.content).text();
        } catch (IOException e) {

        }
        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {

        switch (category){
            case "asianet_sports":
                category_url ="http://kannada.asianetnews.com/sports";
                break;
            case "asianet_cinema":
                category_url ="http://kannada.asianetnews.com/entertainment";
                break;
            case "asianet_technology":
                category_url = "http://kannada.asianetnews.com/technology";
                break;
        }


        try{
            Exception e= new Exception();
            asianet_doc = Jsoup.connect(category_url).get();//this is of type Document
            asianet_elem = asianet_doc.getElementsByClass("col-sm-4 col-xs-6 cl-text-bg").select("a");
            int i;
            for(i=0; i< asianet_elem.size(); i++){

                String link = asianet_elem.get(i).attr("href");
                link = "http://kannada.asianetnews.com"+link;
                String headline = asianet_elem.get(i).select("img:lt(1)").attr("title");
                String img_url = asianet_elem.get(i).select("img:lt(1)").attr("data-original");
                //lt(n) --->elements whose sibling index is less than n

                news.add(new News(headline,link,img_url));
                news.get(i).showNews();
            }


        }catch (Exception e){
        }

        return news;
    }
}
