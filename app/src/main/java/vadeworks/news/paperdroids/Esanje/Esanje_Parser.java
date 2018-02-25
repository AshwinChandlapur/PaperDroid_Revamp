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

    public final String state = "http://www.eesanje.com/category/state-news/";
    public final String national = "http://www.eesanje.com/category/national-news/";
    public final String sports = "http://www.eesanje.com/category/sports/";
    public final String cinema = "http://www.eesanje.com/category/cinema-news/";
    public final String business = "http://www.eesanje.com/category/business/";
    private final ArrayList<News> headlinesList = new ArrayList<>();
    public String headlines_link = "http://www.eesanje.com/category/latest-news/";
    String baseurl = "http://www.eesanje.com/";
    String category_url = "";

    @Override
    public ArrayList<News> parseHeadLines() {
        String inspecturl = "http://www.eesanje.com/category/latest-news/";
        try {

            Document d = Jsoup.connect(inspecturl).timeout(6000).get();

            String imgurl, head, link;

            Elements articles = d.select("div.article-container").first().children();
            for (Element ele : articles) {
                imgurl = ele.select("img").attr("src");
                link = ele.select("header.entry-header").select("a").attr("href");
                head = ele.select("header.entry-header").select("a").text();

                if (!imgurl.isEmpty())
                    headlinesList.add(new News(head, link, imgurl));
            }
        } catch (Exception e) {
            Log.e("exception in es parse", e.toString());

        }
        return headlinesList;
    }

    @Override
    public News parseNewsPost(News news) {
        try {
            String inspecturl = news.link;

            Document d = Jsoup.connect(inspecturl).timeout(6000).get();


            Elements articles = d.select("div.article-content.clearfix").first().select("div.entry-content").first().select("p");

//            System.out.println(articles.size());
            StringBuilder content = new StringBuilder();

            for (Element ele : articles) {
                if (!ele.text().isEmpty())
                    content.append(ele.text()).append("\n\n");
//				System.out.println(ele.text());
            }
            news.imgurl = d.select("div.article-content.clearfix").first().select("div.entry-content").first().select("img").attr("src");
            news.content = content.toString();

            Log.d("esanje content", content.toString());

        } catch (Exception e) {
            Log.e("exception in es parse", e.toString());
        }
        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {
        try {
            Document d = Jsoup.connect(category).get();

            String imgurl, head, link;

            Elements articles = d.select("div.article-container").first().children();
            for (Element ele : articles) {
                imgurl = ele.select("img").attr("src");
                link = ele.select("header.entry-header").select("a").attr("href");
                head = ele.select("header.entry-header").select("a").text();

                if (!imgurl.isEmpty())
                    headlinesList.add(new News(head, link, imgurl));
            }
        } catch (Exception e) {
            Log.e("exception in es parse", e.toString());
        }
        return headlinesList;
    }
}
