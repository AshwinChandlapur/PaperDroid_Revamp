package vadeworks.news.paperdroids;

import java.io.Serializable;

/**
 * Created by ashwinchandlapur on 16/03/18.
 */

public class Articles implements Serializable {
    public String type;
    public String head;
    public String content;
    public String imgurl;
    public String videourl;
    public String audiourl;
    public int articlever;
    public long timestamp;


    public Articles() {
        this.type = "";
        this.head = "";
        this.content = "";
        this.imgurl = "";
        this.videourl = "";
        this.audiourl = "";
        this.articlever = 1;
        this.timestamp = System.currentTimeMillis();
    }

    public Articles(String head, String content, String imgurl) {
        this.head = head;
        this.content = content;
        this.imgurl = imgurl;
    }

    public Articles(String head, String content, String imgurl, String videourl) {
        this.head = head;
        this.content = content;
        this.imgurl = imgurl;
        this.videourl = videourl;
    }

    public Articles(String type, String head, String content, String imgurl, String videourl, String audiourl, int articlever, long timestamp) {
        this.type = type;
        this.head = head;
        this.content = content;
        this.imgurl = imgurl;
        this.videourl = videourl;
        this.audiourl = audiourl;
        this.articlever = articlever;
        this.timestamp = timestamp;
    }

}
