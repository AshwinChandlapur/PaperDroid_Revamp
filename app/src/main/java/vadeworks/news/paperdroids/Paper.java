package vadeworks.news.paperdroids;

import java.util.ArrayList;

/**
 * Created by nikhil on 12/02/18.
 */

public interface Paper{
    public ArrayList<News> parseHeadLines();
    public News parseNewsPost(News news);
    public ArrayList<News> parseCategory(String category);
}