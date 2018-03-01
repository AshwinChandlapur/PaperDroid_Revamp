package vadeworks.news.paperdroids.HorizontalNews;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.Esanje.Esanje_Parser;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.news.paperdroids.UdayaVaani.Udayavaani_Parser;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.news.paperdroids.VijayaVaani.Vijayavaani_Parser;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 27/02/18.
 */

class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<News> mNews = new ArrayList<>();
    News fullnews;
    int mPos;
    String mTag;
    Viewholder viewholder;

    public CustomPagerAdapter(Context context, ArrayList<News> news, int position, String tag) {
        mContext = context;
        mNews = news;
        mPos = position;
        mTag = tag;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.horizontal_pager_item, container, false);
        mPos = position;

        switch (mTag) {
            case Constants.vijayakarnataka:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link);
                        VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews, itemView);
                            }
                        });
                    }
                }).start();

                break;

            case Constants.prajavani:
                fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Prajavaani_Parser parser = new Prajavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews, itemView);
                            }
                        });
                    }
                }).start();
                container.addView(itemView);
                break;

            case Constants.vijayavani:
                fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Vijayavaani_Parser parser = new Vijayavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews, itemView);
                            }
                        });
                    }
                }).start();
                container.addView(itemView);
                break;

            case Constants.udayavani:
                fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Udayavaani_Parser parser = new Udayavaani_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews, itemView);
                            }
                        });
                    }
                }).start();
                container.addView(itemView);
                break;

            case Constants.asianet:
                fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AsiaNet_Parser parser = new AsiaNet_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews, itemView);
                            }
                        });
                    }
                }).start();
                container.addView(itemView);
                break;

            case Constants.esanje:
                fullnews = new News(mNews.get(mPos).head, mNews.get(mPos).link, mNews.get(mPos).imgurl);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Esanje_Parser parser = new Esanje_Parser();
                        fullnews = parser.parseNewsPost(fullnews);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display_news(fullnews, itemView);
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

    private void display_news(News fullnews, View itemView) {

        Viewholder.headlines_textview = itemView.findViewById(R.id.headline);
        Viewholder.content_textview = itemView.findViewById(R.id.content);
        Viewholder.link_textview = itemView.findViewById(R.id.link);
        Viewholder.imageView = itemView.findViewById(R.id.imageView);
        Viewholder.typingView = itemView.findViewById(R.id.loader);
        Viewholder.headlines_textview.setText(fullnews.head);

        if (!fullnews.content.isEmpty()) {
            Viewholder.content_textview.setText(fullnews.content);
        }
        if (!fullnews.imgurl.isEmpty()) {
            Picasso.with(mContext)
                    .load(fullnews.imgurl)
                    .placeholder(R.drawable.image3)
                    .error(R.drawable.image3)
                    .into(Viewholder.imageView);
        }

        Viewholder.link_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Viewholder.headlines_textview.setVisibility(View.GONE);
            }
        });
        Viewholder.typingView.setVisibility(View.GONE);
    }

    static class Viewholder {
        static TextView content_textview;
        static TextView link_textview;
        static ImageView imageView;
        static TypingIndicatorView typingView;
        static TextView headlines_textview;
    }

}