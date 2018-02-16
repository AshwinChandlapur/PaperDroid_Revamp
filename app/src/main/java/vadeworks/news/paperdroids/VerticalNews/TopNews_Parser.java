package vadeworks.news.paperdroids.VerticalNews;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.News;


import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;

/**
 * Created by ashwinchandlapur on 14/02/18.
 */

public class TopNews_Parser implements Paper {
    ArrayList<News> headlinesList = new ArrayList<News>();
    String udayavaani_base_url = "https://www.udayavani.com/";
    String category_url;
    Document udayavaani_doc;
    Elements udayavaani_elem,udayavaani_elem1;
    ArrayList<News> news = new ArrayList<News>();
    String content = "";
    public String sports = "https://www.udayavani.com/kannada/category/sports-news";
    public String cinema = "https://www.udayavani.com/kannada/category/bollywood-news";
    public String world ="https://www.udayavani.com/kannada/category/world-news";
    public String business = "https://www.udayavani.com/kannada/category/business-news";
    String link,head,imgurl;

    @Override
    public ArrayList<News> parseHeadLines() {

        try{
            udayavaani_doc =Jsoup.connect(udayavaani_base_url).get();
            Log.d("Document is","Docu is"+udayavaani_doc);
            udayavaani_elem = udayavaani_doc.getElementsByClass("item-list").select("ul").select("li").select("div:eq(2)").select("a");
            udayavaani_elem1 = udayavaani_doc.getElementsByClass("item-list").select("ul").select("li").select("div:eq(3)").select("span");
            Log.d("Udayavani Elem","elem"+udayavaani_elem);
            Log.d("Udayavani Elem","elem1"+udayavaani_elem1);
            int i;
            for(i=0; i< udayavaani_elem.size(); i++){

                String link = udayavaani_elem.get(i).attr("href");
                link = "https://www.udayavani.com"+link;
                String headline = udayavaani_elem.get(i).text();
                String content = udayavaani_elem1.get(i).text();
                news.add(new News(headline,link,"",content));
                news.get(i).showNews();
            }


        }catch (Exception e){

        }

        return news;
    }

    @Override
    public News parseNewsPost(News news) {

        try {
            udayavaani_doc = Jsoup.connect(news.link).get();
            udayavaani_elem = udayavaani_doc.getElementsByClass("field-item even").select("p");
            for (Element ele: udayavaani_elem) {
                if (!ele.text().isEmpty())
                    content = content + ele.text() + "\n\n";
            }
            news.content = content;

            udayavaani_elem = udayavaani_doc.getElementsByClass("field-item even").select("img");
            news.imgurl = udayavaani_elem.get(1).attr("src");

        } catch (IOException e) {

        }
        return news;

    }

    @Override
    public ArrayList<News> parseCategory(String category) {

        return null;
    }


}
