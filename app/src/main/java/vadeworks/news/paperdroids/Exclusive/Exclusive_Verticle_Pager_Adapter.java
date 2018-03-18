package vadeworks.news.paperdroids.Exclusive;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import vadeworks.news.paperdroids.Articles;
import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.ProxyFactory;
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
    private String proxyUrl;
    private String notifId;



    ProgressDialog pd;


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

        container.removeView((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        JZVideoPlayer.releaseAllVideos();
        ProxyFactory proxyFactory = new ProxyFactory();
        HttpProxyCacheServer proxy= proxyFactory.getProxy(mContext);
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
                proxyUrl = proxy.getProxyUrl(articleList.get(position).videourl);
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
        JZVideoPlayer.releaseAllVideos();
        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        image = itemView.findViewById(R.id.image);
        headline.setText(singleArticle.head);
        content.setText(singleArticle.content);
        Picasso.with(mContext).load(singleArticle.imgurl).fit().into(image);
    }

    private void verticalNewsDisplay_ytv(final Articles singleArticle, View itemView) {
        JZVideoPlayer.releaseAllVideos();
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
                intent.putExtra("backgroundImg",singleArticle.imgurl);
                mContext.startActivity(intent);
            }
        });
    }

    private void verticalNewsDisplay_vid(final Articles singleArticle,View itemView){
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(proxyUrl
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, singleArticle.head);
       jzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(singleArticle.imgurl));
        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        headline.setText(singleArticle.head);
        content.setText(singleArticle.content);
    }

}
