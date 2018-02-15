package vadeworks.news.paperdroids.Esanje;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;

/**
 * Created by ashwinchandlapur on 15/02/18.
 */

public class Esanje_Parser implements Paper {

    ArrayList<News> headlinesList = new ArrayList<News>();

    String baseurl = "http://www.eesanje.com/";
    String category_url = "";
    public String headlines_link = "http://www.eesanje.com/category/latest-news/";
    public String state = "http://www.eesanje.com/category/state-news/";
    public String country = "http://www.eesanje.com/category/national-news/";
    public String sports = "http://www.eesanje.com/category/sports/";
    public String cinema = "http://www.eesanje.com/category/cinema-news/";
    public String business = "http://www.eesanje.com/category/business/";

    @Override
    public ArrayList<News> parseHeadLines() {
        String inspecturl= "http://www.eesanje.com/category/latest-news/";
        try {
            Document d= Jsoup.connect(inspecturl).timeout(6000).get();

            String imgurl, head, link;

            Elements articles = d.select("div.article-container").first().children();
            for (Element ele : articles) {
                imgurl = ele.select("img").attr("src");
                link= ele.select("header.entry-header").select("a").attr("href");
                head = ele.select("header.entry-header").select("a").text();

                if(!imgurl.isEmpty())
                    headlinesList.add(new News(head, link, imgurl));
            }
        }catch (Exception e){
            Log.e("exception in es parse", e.toString());
        }
        return headlinesList;
    }

    @Override
    public News parseNewsPost(News news) {
        try {
            String inspecturl= news.link;

            Document d=Jsoup.connect(inspecturl).timeout(6000).get();


            Elements articles = d.select("div.article-content.clearfix").first().select("div.entry-content").first().select("p");

//            System.out.println(articles.size());
            String content ="";

            for (Element ele : articles) {
                if (!ele.text().isEmpty())
                    content = content + ele.text() + "\n\n";
//				System.out.println(ele.text());
            }
            news.imgurl= d.select("div.article-content.clearfix").first().select("div.entry-content").first().select("img").attr("src");
            news.content=content;

        }catch (Exception e){
            Log.e("exception in es parse", e.toString());
        }
        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {
        try {
            Document d= Jsoup.connect(category).timeout(6000).get();

            String imgurl, head, link;

            Elements articles = d.select("div.article-container").first().children();
            for (Element ele : articles) {
                imgurl = ele.select("img").attr("src");
                link= ele.select("header.entry-header").select("a").attr("href");
                head = ele.select("header.entry-header").select("a").text();

                if(!imgurl.isEmpty())
                    headlinesList.add(new News(head, link, imgurl));
            }
        }catch (Exception e){
            Log.e("exception in es parse", e.toString());
        }
        return headlinesList;
    }
}
