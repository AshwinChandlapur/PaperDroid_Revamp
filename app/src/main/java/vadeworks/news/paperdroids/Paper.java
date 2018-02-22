package vadeworks.news.paperdroids;

import java.util.ArrayList;

/**
 * Created by ashwinchandlapur on 13/02/18.
 */

public interface Paper{
    ArrayList<News> parseHeadLines();
    News parseNewsPost(News news);
    ArrayList<News> parseCategory(String category);
}