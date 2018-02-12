package vadeworks.news.paperdroids;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by nikhil on 12/02/18.
 */

public class VijayaVani implements Paper{



    @Override
    public ArrayList<News> parseHeadLines() {
        ArrayList<News> headlinesList = new ArrayList<News>();
        try{

            Document d= Jsoup.connect("http://vijayavani.net/").timeout(6000).get();

            String head = d.getElementsByClass("ftrd-title").first().select("a.black").text();
            String link = d.getElementsByClass("ftrd-title").first().select("a.black").attr("href");
            String imgurl = d.select("div.small-12.medium-7.large-7.columns.alpha.ftrd-img").first()
                    .select("img").attr("src");

            headlinesList.add(new News(head, link, imgurl));

            d.select("div.full.home_space_1").first().child(0).remove();

            Elements sndlist = d.select("div.full.home_space_1").first().children();
            for (Element element : sndlist) {
                link= element.select("a").attr("href");
                imgurl= element.select("img").attr("src");
                head= element.text();
                headlinesList.add(new News(head, link, imgurl));
            }

            Elements sidebar = d.select("div.full.sidebar_space").first().getElementsByClass("black");
            for (Element element : sidebar) {
                link= element.select("a").attr("href");
                imgurl= element.select("img").attr("src");
                head= element.text();
                headlinesList.add(new News(head, link, imgurl));
            }

        }
        catch(Exception e) {
//         Log.e("exception", e.toString());
        }

        return headlinesList;
    }

    @Override
    public News parseNewsPost(News news) {
        try {
            Document d = Jsoup.connect(news.head).timeout(6000).get();
            String imgurl = d.select("div.full.post-01-img").first().select("img").attr("src");
            Elements childs = d.select("div.full.post-01-content").first().select("p");
            String body = "";

            for (Element element : childs) {
                body = body + element.text() + "\n\n";
            }
            news.imgurl= imgurl;
            news.content= body;
            Log.d("content",body);
        }catch (Exception e){

        }

        return news;
    }

    @Override
    public ArrayList<News> parseCategory(String category) {
        try{
            ArrayList<News> headlinesList = new ArrayList<News>();

            String inspecturl= categoryUrl(category);

            Document d=Jsoup.connect(inspecturl).timeout(6000).get();

            d.select("div.full.inpage_cotent").first().select("header.page-header").first().remove();
            d.select("div.full.inpage_cotent").first().select("div.full.archnav").first().remove();

            Elements contentlist = d.select("div.full.inpage_cotent").first().children();

            for (Element element : contentlist) {
                element.select("p").remove();
            }

            String link = contentlist.first().select("a").attr("href");
            String imgurl = contentlist.first().select("img").attr("src");
            String head = contentlist.first().text();
            headlinesList.add(new News(head, link, imgurl));

            contentlist.remove(0);

            d.select("div.full.inpage_cotent").first().remove();
//		System.out.println("here");
//		System.out.println(contentlist.first());

            for (Element element : contentlist) {
                imgurl= element.select("img").attr("src");
                head=element.text();
                link= element.select("a.black").attr("href");
                headlinesList.add(new News(head, link, imgurl));
            }

            System.out.println(headlinesList.size());
            for (News n : headlinesList) {
                n.showNews();
                System.out.println("\n");
            }

        }catch (Exception e){

        }
        return null;
    }


    public String categoryUrl(String tag){
        switch (tag){
            case "sports" : return "";
            case "politics" : return "http://vijayavani.net/category/politics/";
            default: return "";
        }
    }
}
