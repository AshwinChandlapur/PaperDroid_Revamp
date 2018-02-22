package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 19/02/18.
 */

public class ThreadStarter_VK {

    ArrayList<News> news = new ArrayList<>();
    ListView listView;
    Context mContext;
    ViewHolder viewHolder;
    TypingIndicatorView typingView;
    String mCategory;
    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }

    VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();

    public void threadShuruKaro(final FragmentActivity fragmentActivity, Context context, View view, final String category ){

        listView = view.findViewById(R.id.vk_news);
        typingView = view.findViewById(R.id.loader);
        mContext = context;
        mCategory = category;


        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (mCategory){
                    case "headlines":
                        news = parser.parseHeadLines();
                        break;

                    case "sports":
                        news = parser.parseCategory(parser.sports);
                        break;

                    case "cinema":
                        news = parser.parseCategory(parser.cinema);
                        break;

                    case "lifestyle":
                        news = parser.parseCategory(parser.lifestyle);
                        break;

                    case "technology":
                        news = parser.parseCategory(parser.technology);
                        break;


                }


                if(fragmentActivity==null){
                    return;
                }
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new ListView_Adapter<News>(mContext, news) {
                            @Override
                            public View getMyView(int i, View view, ViewGroup parent, News news) {
                                if ((view == null) || (view.getTag() == null)) {
                                    view =fragmentActivity.getLayoutInflater().inflate(R.layout.listview_custom_layout, null);
                                    viewHolder = new ViewHolder();
                                } else {
                                    viewHolder = (ViewHolder) view.getTag();
                                }
                                ViewHolder.news_headline = view.findViewById(R.id.newsHeadlines);
                                ViewHolder.news_image = view.findViewById(R.id.newsImage);
                                view.setTag(viewHolder);
                                ViewHolder.news_headline.setText(news.head);
                                if (!news.imgurl.isEmpty()) {
                                    Picasso.with(mContext).load(news.imgurl).into(ViewHolder.news_image);
                                } else {
                                    ViewHolder.news_image.setVisibility(View.GONE);
                                }

                                return view;
                            }
                        });
                        typingView.setVisibility(View.GONE);
                    }
                });
            }

        }).start();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(fragmentActivity, Display_news.class);
                i.putExtra("singleHead", news.get(position).head);
                i.putExtra("singleLink", news.get(position).link);
                i.putExtra("singleImg", news.get(position).imgurl);
                i.putExtra("tag", "vijayakarnataka");
                fragmentActivity.startActivity(i);
            }
        });

    }



}
