package vadeworks.news.paperdroids.Exclusive;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDataSource;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import vadeworks.news.paperdroids.Articles;
import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.MainScreen.MainScreen_Activity;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Paper;
import vadeworks.news.paperdroids.VerticalNews.Vertical_News;
import vadeworks.news.paperdroids.YouTube;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 16/02/18.
 */

class Exclusive_Verticle_Pager_Adapter extends PagerAdapter {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<Articles> articleList = new ArrayList<>();
    private TextView headline;
    private ImageView image,youTube;
    private TextView content;
    private Articles fullArticle;
    private TextView link;


    public Exclusive_Verticle_Pager_Adapter(Context context, ArrayList<Articles> articles) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        articleList= articles;
        Log.d("ArticleList size", "ArticleList Size" + articleList.size());
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        JZVideoPlayer.releaseAllVideos();
        container.removeView((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        JZVideoPlayer.releaseAllVideos();
        View itemView;
        switch (articleList.get(position).type){

            case Constants.type_img:
                itemView = mLayoutInflater.inflate(R.layout.exclusive_article_display_img, container, false);
                fullArticle = new Articles(articleList.get(position).head, articleList.get(position).content, articleList.get(position).imgurl);
                verticalNewsDisplay_img(fullArticle, itemView);
                break;

            case Constants.type_ytv:
                itemView = mLayoutInflater.inflate(R.layout.exclusive_article_display_ytv, container, false);
                fullArticle = new Articles(articleList.get(position).head, articleList.get(position).content, articleList.get(position).imgurl,articleList.get(position).videourl);
                verticalNewsDisplay_ytv(fullArticle, itemView);
                break;

            case Constants.type_vid:
                itemView = mLayoutInflater.inflate(R.layout.exclusive_article_display_vid, container, false);
                fullArticle = new Articles(articleList.get(position).head, articleList.get(position).content, articleList.get(position).imgurl,articleList.get(position).videourl);
                verticalNewsDisplay_vid(fullArticle, itemView);
                break;

            default:
                itemView = mLayoutInflater.inflate(R.layout.exclusive_article_display_img, container, false);
                fullArticle = new Articles(articleList.get(position).head, articleList.get(position).content, articleList.get(position).imgurl);
                verticalNewsDisplay_img(fullArticle, itemView);
        }
        container.addView(itemView);
        return itemView;
    }


    private void verticalNewsDisplay_img(Articles singleArticle, View itemView) {

        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        image = itemView.findViewById(R.id.image);
        headline.setText(singleArticle.head);
        content.setText(singleArticle.content);
        Picasso.with(mContext).load(singleArticle.imgurl).fit().into(image);
    }

    private void verticalNewsDisplay_ytv(final Articles singleArticle, View itemView) {

        FrameLayout youtubeFrame = itemView.findViewById(R.id.youtubeFrame);
        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        image = itemView.findViewById(R.id.image);
        youTube = itemView.findViewById(R.id.youTube);
        headline.setText(singleArticle.head);
        content.setText(singleArticle.content);
        Picasso.with(mContext).load(singleArticle.imgurl).fit().into(image);

        youtubeFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), YouTube.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("youtubeLink",singleArticle.videourl);
                mContext.startActivity(intent);
            }
        });
    }

    private void verticalNewsDisplay_vid(final Articles singleArticle,View itemView){
        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(singleArticle.videourl
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "NewsDuniya");
        headline.setText(singleArticle.head);
        content.setText(singleArticle.content);
    }
}
