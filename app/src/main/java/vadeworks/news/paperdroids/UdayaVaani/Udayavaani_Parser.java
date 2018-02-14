package vadeworks.news.paperdroids.UdayaVaani;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;

/**
 * Created by ashwinchandlapur on 14/02/18.
 */

public class Udayavaani_Parser implements Paper {


    String udayavaani_base_url = "https://www.udayavani.com/";
    String category_url;
    Document udayavaani_doc;
    Elements udayavaani_elem;
    ArrayList<News> news = new ArrayList<News>();
    String content = "";
    String sports = "https://www.udayavani.com/kannada/category/sports-news";
    String cinema = "https://www.udayavani.com/kannada/category/bollywood-news";
    String link,head,imgurl;


    @Override
    public ArrayList<News> parseHeadLines() {

        try{
            udayavaani_doc = Jsoup.connect(udayavaani_base_url).get();//this is of type Document
            udayavaani_elem = udayavaani_doc.getElementsByClass("item-list").select("ul").select("li").select("div:eq(2)").select("a");
            Log.d("Udayavani Elem","Udayavani elem"+udayavaani_elem);
            int i;
            for(i=0; i< udayavaani_elem.size(); i++){

                String link = udayavaani_elem.get(i).attr("href");
                link = "https://www.udayavani.com"+link;
                String headline = udayavaani_elem.get(i).text();
                news.add(new News(headline,link));
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

        switch (category){
            case "sports" :
                category_url = sports;
                break;
            case "cinema":
                category_url = cinema;
                break;
        }
        try{
            udayavaani_doc = Jsoup.connect(category_url).get();
            udayavaani_elem = udayavaani_doc.select("div.view-taxonomy-term").get(1).select("div.view-content").first().children();
            for (Element ele : udayavaani_elem) {
                imgurl= ele.select("div.field-content").select("img").attr("data-src");
                link = "https://www.udayavani.com/" + ele.select("div.field-content").select("a").attr("href");
                head = ele.select("span.field-content").select("a").text();
                Log.d("log","logoi"+head);
                Log.d("log","logoi"+link);
                Log.d("log","logoi"+imgurl);
                news.add(new News(head, link, imgurl));
            }


        }catch (Exception e){

        }

        return news;
    }
}
