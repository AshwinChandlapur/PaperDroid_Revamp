package vadeworks.news.paperdroids.AsiaNet;

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
 * Created by ashwinchandlapur on 12/02/18.
 */

public class AsiaNet_Parser implements Paper {


    public final String sports = "http://kannada.asianetnews.com/sports";
    public final String cinema = "http://kannada.asianetnews.com/entertainment";
    public final String technology = "http://kannada.asianetnews.com/technology";
    public final String lifestyle = "http://kannada.asianetnews.com/life";
    private final String asianet_base_url = "http://kannada.asianetnews.com/";
    private final ArrayList<News> news = new ArrayList<>();
    private Document asianet_doc;
    private Elements asianet_elem;

    @Override
    public ArrayList<News> parseHeadLines() {

        try {
            asianet_doc = Jsoup.connect(asianet_base_url).get();//this is of type Document
            asianet_elem = asianet_doc.getElementsByClass("col-sm-4 col-xs-6 cl-text-bg").select("a");
            int i;
            for (i = 0; i < asianet_elem.size(); i++) {

                String link = asianet_elem.get(i).attr("href");
                link = "http://kannada.asianetnews.com" + link;
                String headline = asianet_elem.get(i).select("img:lt(1)").attr("title");
                String img_url = asianet_elem.get(i).select("img:lt(1)").attr("data-original");
                //lt(n) --->elements whose sibling index is less than n

                news.add(new News(headline, link, img_url));
                news.get(i).showNews();
            }

        } catch (Exception e) {
            Log.d("Exceptions", e.toString());
        }
        return news;
    }

    @Override
    public News parseNewsPost(News news) {
        StringBuilder content = new StringBuilder();
        try {
            asianet_doc = Jsoup.connect(news.link).get();
            asianet_elem = asianet_doc.getElementsByClass("article-wrap new-article-desc").select("p");
            for (Element ele : asianet_elem) {
                if (!ele.text().isEmpty())
                    content.append(ele.text()).append("\n\n");
            }
            news.content = content.toString();
            asianet_elem = asianet_doc.getElementsByClass("col-md-12 new-article-header").select("div").select("img");
            news.imgurl = asianet_elem.attr("src");
            Log.d("content", "content" + news.content);


        } catch (IOException e) {
            Log.d("Exceptions", e.toString());
        }
        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {


        try {
            asianet_doc = Jsoup.connect(category).get();//this is of type Document
            asianet_elem = asianet_doc.getElementsByClass("col-sm-4 col-xs-6 cl-text-bg").select("a");
            int i;
            for (i = 0; i < asianet_elem.size(); i++) {

                String link = asianet_elem.get(i).attr("href");
                link = "http://kannada.asianetnews.com" + link;
                String headline = asianet_elem.get(i).select("img:lt(1)").attr("title");
                String img_url = asianet_elem.get(i).select("img:lt(1)").attr("data-original");
                //lt(n) --->elements whose sibling index is less than n

                news.add(new News(headline, link, img_url));
                news.get(i).showNews();
            }


        } catch (Exception e) {
            Log.d("Exceptions", e.toString());
        }

        return news;
    }
}
