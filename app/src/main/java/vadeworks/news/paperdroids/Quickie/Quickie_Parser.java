package vadeworks.news.paperdroids.Quickie;

import android.util.Log;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;

public class Quickie_Parser {





    private String news_60_kannada="https://www.60secondsnow.com/kn/";
    private String news_60_english="https://www.60secondsnow.com/india/";
    private String news_60_hindi ="https://www.60secondsnow.com/hi/";


    private ArrayList<News> headlinesList = new ArrayList<>();
    int i;

    public ArrayList<News> MainPage(String category){

        String inspecturl = new String();

        switch (category){
            case "HT_HEADLINES":
                inspecturl= news_60_english;
                break;
            case "ND_HEADLINES":
                inspecturl= news_60_hindi;
                break;
            case "PJ_HEADLINES":
                inspecturl= news_60_kannada;
                break;
        }

        try {
            Document d= Jsoup.connect(inspecturl).get();

            Elements topstories = d.getElementsByClass("listingpage").select("article");
            Log.d("Main", "TopStories: "+topstories.toString());
            String head;
            String imgurl;
            String link;
            String content;

            for (Element ele : topstories) {
                head= ele.select("div").select("div:eq(1)").select("h2").text();
                imgurl= ele.select("div:eq(0)").select("img").attr("src");
                imgurl = imgurl.replace("400x99/","");
                content = ele.select("div").select("div:eq(1)").select("div.article-desc").text();
                if (!head.isEmpty())
                    headlinesList.add(new News(head, imgurl,content));
                Log.d("Main", "MainPage: "+imgurl);
            }

        }catch (Exception e){
            Log.e("Exception in pv head", e.toString());
        }
        return headlinesList;
    }

}
