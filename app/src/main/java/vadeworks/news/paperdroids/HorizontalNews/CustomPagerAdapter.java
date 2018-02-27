package vadeworks.news.paperdroids.HorizontalNews;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 27/02/18.
 */

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> mNews = new ArrayList<News>();
    News fullnews;
    int mPos;
    String mTag;


    public CustomPagerAdapter(Context context, ArrayList<News> news,int position,String tag) {
        mContext = context;
        mNews = news;
        mPos = position;
        mTag= tag;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    Viewholder viewholder;

    static class Viewholder{
        static TextView content_textview;
        static TextView link_textview;
        static ImageView imageView;
        static TypingIndicatorView typingView;
        static TextView headlines_textview;
    }


    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((CoordinatorLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        mPos = position;

        switch (mTag){
            case "vijayakarnataka":
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link);
                        VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(fullnews.content==null){
                                    container.removeView(itemView);
                                }else{
                                    display_news(fullnews,itemView);
                                }
                            }
                        });
                    }
                }).start();
                container.addView(itemView);
                break;

            case "prajavani":
                fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Prajavaani_Parser parser = new Prajavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews,itemView);
                            }
                        });
                    }
                }).start();
                container.addView(itemView);
                break;

        }


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CoordinatorLayout) object);
    }


    private void display_news(News fullnews,View itemView) {

        viewholder.headlines_textview = itemView.findViewById(R.id.headline);
        viewholder.content_textview = itemView.findViewById(R.id.content);
        viewholder.link_textview = itemView.findViewById(R.id.link);
        viewholder.imageView = itemView.findViewById(R.id.imageView);
        viewholder.typingView = itemView.findViewById(R.id.loader);
        viewholder.headlines_textview.setText(fullnews.head);
        if (!fullnews.content.isEmpty()) {
            viewholder.content_textview.setText(fullnews.content);
        }
        if (!fullnews.imgurl.isEmpty()) {
            Picasso.with(mContext)
                    .load(fullnews.imgurl)
                    .placeholder(R.drawable.image3)
                    .error(R.drawable.image3)
                    .into(viewholder.imageView);
        }

        viewholder.link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewholder.headlines_textview.setVisibility(View.GONE);
            }
        });
        viewholder.typingView.setVisibility(View.GONE);
    }

}