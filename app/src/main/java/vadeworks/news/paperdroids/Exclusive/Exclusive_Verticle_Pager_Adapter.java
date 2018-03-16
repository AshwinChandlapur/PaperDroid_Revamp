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
    private ArrayList<News> mnews = new ArrayList<>();
    private TextView headline;
    private ImageView image,youTube;
    private TextView content;
    private News fullnews;
    private TextView link;


    public Exclusive_Verticle_Pager_Adapter(Context context, ArrayList<News> news) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mnews = news;
        Log.d("Mnews size", "Mnews Size" + mnews.size());
    }

    @Override
    public int getCount() {
        return mnews.size();
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

        final View itemView = mLayoutInflater.inflate(R.layout.exclusive_article_display, container, false);
        fullnews = new News(mnews.get(position).head, mnews.get(position).link, mnews.get(position).imgurl, mnews.get(position).content);
        verticalNewsDisplay(fullnews, itemView);
        container.addView(itemView);

        return itemView;
    }


    private void verticalNewsDisplay(final News singleNews, View itemView) {

        headline = itemView.findViewById(R.id.headline);
        content = itemView.findViewById(R.id.content);
        link = itemView.findViewById(R.id.link);
        image = itemView.findViewById(R.id.image);
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        youTube = itemView.findViewById(R.id.youTube);


        if(singleNews.imgurl.contains("firebasestorage.googleapis.com/v0/b/newsguru15022018.appspot.com"))
        {
            jzVideoPlayerStandard.setVisibility(View.GONE);
            youTube.setVisibility(View.GONE);
        }else if(singleNews.imgurl.contains(".mp4"))
        {
            youTube.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            jzVideoPlayerStandard.setUp(Constants.mp4_link
                    , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "NewsDuniya");
//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
        }else{
            image.setVisibility(View.GONE);
            jzVideoPlayerStandard.setVisibility(View.GONE);
            youTube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext.getApplicationContext(),YouTube.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
        }






        headline.setText(singleNews.head);
        content.setText(singleNews.content);
        Picasso.with(mContext).load(singleNews.imgurl).fit().into(image);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(singleNews.link));
                mContext.startActivity(browserIntent);
            }
        });
    }
}
