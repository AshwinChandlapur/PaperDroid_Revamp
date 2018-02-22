package vadeworks.news.paperdroids.Prajavani;

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

public class Prajavaani_Parser implements Paper {
    ArrayList<News> headlinesList = new ArrayList<>();

    String baseurl = "http://www.prajavani.net";
    String category_url = "";
    public String state = "http://www.prajavani.net/news/category/30.html";
    public String country = "http://www.prajavani.net/news/category/31.html";
    public String sports = "http://www.prajavani.net/news/category/64.html";
    public String cinema = "http://www.prajavani.net/news/category/138.html";
    public String business = "http://www.prajavani.net/news/category/136.html";
    String head, link, imgurl;

    @Override
    public ArrayList<News> parseHeadLines() {
        try {
            String inspecturl= "http://www.prajavani.net/";

            Document d= Jsoup.connect(inspecturl).get();

            Elements topstories = d.select("div.top_news").first().select("div.story_block");

            String head;
            String imgurl;
            String link;

            for (Element ele : topstories) {
                head= ele.select("a").attr("title");
                imgurl= ele.select("img").attr("src");
                link = baseurl+ele.select("a").attr("href");
                if (!head.isEmpty())
                    headlinesList.add(new News(head, link, imgurl));
            }

        }catch (Exception e){
            Log.e("Exception in pv head", e.toString());
        }
        return headlinesList;
    }

    @Override
    public News parseNewsPost(News news) {
        try {
            String inspecturl= news.link;

            Document d=Jsoup.connect(inspecturl).get();
            Elements body = d.select("div.body");
            news.imgurl= d.select("div.article_image").select("img").attr("src");
//            System.out.println(body.first().children().select("blockquote"));
            if (!body.select("blockquote").isEmpty())
            {
                body.select("blockquote").remove();
                body.select("script").remove();
            }
            StringBuilder content = new StringBuilder();
            String tmp = "";

            for (Element ele : body.first().children()) {
                tmp= ele.select("p").text();
                if (!tmp.isEmpty())
                    content.append(tmp).append("\n\n");
            }
            news.content= content.toString();
        }catch (Exception e){
            Log.e("Exception in vv post", e.toString());
        }
        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {
        try{

            Document d=Jsoup.connect(category).get();
            Elements topstories = d.select("div.main_block.col-lg-8.story_block").first().select("div.story_block").select("div.story_block");

//            System.out.println(topstories.size());

            topstories.select("span.kicker").remove();

            for (Element ele : topstories) {
                ele.select("div.meta_area").remove();
                ele.select("div.summary").remove();
                head= ele.select("div.story_text").text();
                imgurl= ele.select("img").attr("src");
                link = baseurl+ele.select("a").attr("href");
                if ((!head.isEmpty()) && (!imgurl.isEmpty()) )
                    headlinesList.add(new News(head, link, imgurl));
            }

            headlinesList.remove(0);

        }catch (Exception e){
            Log.e("exception in pv cat", e.toString());
        }
        return headlinesList;
    }


}
