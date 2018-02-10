package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.util.Log;

/**
 * Created by ashwinchandlapur on 10/02/18.
 */

public class News {
    String head;
    String imgurl;
    String link;
    String content;

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
        Log.d("news-info", this.head);
        Log.d("news-info", this.link);
        Log.d("news-info", this.imgurl);
        Log.d("news-info", this.content);
    }

}
