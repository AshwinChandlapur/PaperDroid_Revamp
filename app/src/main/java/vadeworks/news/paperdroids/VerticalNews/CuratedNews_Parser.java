package vadeworks.news.paperdroids.VerticalNews;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;

/**
 * Created by ashwinchandlapur on 26/02/18.
 */

public class CuratedNews_Parser {

    private final String top_10_base_url = "https://pastebin.com/raw/YEf6BeBz";
    private final ArrayList<News> news = new ArrayList<>();
    private Document top10_doc;
    private Elements top10_elem;
    private String body;

    public ArrayList<News> parseTop10(String verticalLink) {



        try {
            top10_doc = Jsoup.connect(verticalLink).get();
            Log.d("Document is", "Docu is" + top10_doc);

            top10_elem = top10_doc.getElementsByClass("paste_code");
            Log.d("Document is", "docu is body" + top10_elem);
            body = top10_elem.toString();
            body = Jsoup.parse(body).text();
            Log.d("Document is", "docu is body" + body);
            try {
                JSONObject jsonObj = new JSONObject(top10_elem.text());


                JSONArray top10 = jsonObj.getJSONArray("topten");


                int i;
                for (i = 0; i < top10.length(); i++) {
                    JSONObject t = top10.getJSONObject(i);

                    String head = t.getString("head");
                    String link = t.getString("link");
                    String imgurl = t.getString("imgurl");
                    String content = t.getString("content");

                    Log.d("Content is", "head " + head);
                    Log.d("Content is", "link " + link);
                    Log.d("Content is", "imgurl " + imgurl);
                    Log.d("Content is", "content " + content);

                    news.add(new News(head, link, imgurl, content));
                    news.get(i).showNews();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return news;
    }
}
