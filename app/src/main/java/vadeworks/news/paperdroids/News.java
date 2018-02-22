package vadeworks.news.paperdroids;

import android.util.Log;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class News {
    public final String head;
    public String imgurl;
    public final String link;
    public String content;
    public  String tag;

    public News() {
        this.head= "";
        this.link= "";
        this.imgurl= "";
        this.content="";
    }


    public News(String head, String link) {
        this.head= head;
        this.link= link;
        this.imgurl= "";
        this.content="";
    }

    public News(String head, String link, String imgurl) {
        this.head= head;
        this.link= link;
        this.imgurl= imgurl;
        this.content="";
    }


    public News(String head, String link, String imgurl, String content) {
        this.head= head;
        this.link= link;
        this.imgurl= imgurl;
        this.content= content;
    }

    public void showNews() {
        Log.d("news-info", "Headline is    "+this.head);
        Log.d("news-info", "Link is   "+this.link);
        Log.d("news-info", "imageUrl is   "+this.imgurl);
        Log.d("news-info", "Content is "+this.content);

    }

}
