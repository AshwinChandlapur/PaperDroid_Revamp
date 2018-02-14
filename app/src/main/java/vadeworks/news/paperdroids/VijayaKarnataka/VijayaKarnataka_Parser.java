package vadeworks.news.paperdroids.VijayaKarnataka;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class VijayaKarnataka_Parser implements Paper {

    String vijayakarnataka_base_url = "https://vijaykarnataka.indiatimes.com/";
    String category_url;
    String link_picker;
    Document vijayakarnataka_doc;
    Elements vijayakarnataka_elem;
    ArrayList<News> news = new ArrayList<News>();
    String sports="nav10738520",cinema="nav10738512",lifestyle="nav57869229",technology="nav60023487";
    String link,imgurl,headline;


    @Override
    public ArrayList<News> parseHeadLines() {

        try {
            vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_base_url).get();
            vijayakarnataka_elem = vijayakarnataka_doc.getElementsByClass("other_main_news1").select("ul").select("li").select("a");//this has the headline
            //vijayakarnataka_headlines_elem is of type Elements

            int i;
            for(i=0;i<vijayakarnataka_elem.size();i++){

                String link = vijayakarnataka_base_url+vijayakarnataka_elem.get(i).attr("href");
                String headline = vijayakarnataka_elem.get(i).text();
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
            vijayakarnataka_doc = Jsoup.connect(news.link).get();
            vijayakarnataka_elem = vijayakarnataka_doc.getElementsByClass("thumbImage").select("img");
            try {
                news.imgurl = "https://vijaykarnataka.indiatimes.com" + vijayakarnataka_elem.first().attr("src");
            } catch (Exception e) {
                Log.d("error", "error");
            }

            vijayakarnataka_elem = vijayakarnataka_doc.getElementsByTag("arttextxml");
            news.content = vijayakarnataka_elem.toString();
            news.content = Jsoup.parse(news.content).text();
            Log.d("parser","parser"+news.head);
            Log.d("parser","parser"+news.link);
            Log.d("parser","parser"+ news.content);
            Log.d("parser","parser"+news.imgurl);
        }catch (Exception e){

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
            case "lifestyle":
                    category_url = lifestyle;
                break;
            case "technology":
                    category_url = technology;
                break;

        }


        try{
            vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_base_url).get();
            vijayakarnataka_elem = vijayakarnataka_doc.getElementById(category_url).select("a");
            link_picker = vijayakarnataka_elem.attr("href");
            link_picker = vijayakarnataka_base_url+link_picker;
        }catch (Exception e){
            Log.d("error","error");
        }




        try{
            vijayakarnataka_doc = Jsoup.connect(link_picker).get();
            vijayakarnataka_elem = vijayakarnataka_doc.getElementsByClass("dvlstimgs").select("a");
            Log.d("Elem elem","elem elem"+vijayakarnataka_elem);
            int i;
            for(i=0;i<vijayakarnataka_elem.size();i++){
                link =vijayakarnataka_doc.getElementsByClass("dvlstimgs").select("a").get(i).attr("href");
                //for a weird Reason  vijayakarnataka_doc.getElementsByClass("dvlstimgs").select("a") cannot be substituted by vijayakarnataka_elem
                link = vijayakarnataka_base_url+link;
                Log.d("sports-url","sports-link "+link);


                vijayakarnataka_elem = vijayakarnataka_elem.select("img");
                imgurl = vijayakarnataka_elem.get(i).attr("src");
                imgurl = vijayakarnataka_base_url+imgurl;
                Log.d("sports-url","sports-image "+imgurl);


                headline = vijayakarnataka_elem.get(i).attr("title");
                Log.d("sports-url","sports-headline "+headline);

                news.add(new News(headline,link,imgurl));
            }
        }catch (Exception e){
        }


        return news;
    }



}
