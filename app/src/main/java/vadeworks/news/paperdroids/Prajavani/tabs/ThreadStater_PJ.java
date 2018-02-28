package vadeworks.news.paperdroids.Prajavani.tabs;

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

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.HorizontalNews.Horizontal_Display_News;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.Prajavaani_Parser;
import vadeworks.paperdroid.R;

/**
 * Created by ashwinchandlapur on 19/02/18.
 */

class ThreadStater_PJ {

    private final Prajavaani_Parser parser = new Prajavaani_Parser();
    private ArrayList<News> news = new ArrayList<>();
    private ListView listView;
    private Context mContext;
    private ViewHolder viewHolder;
    private TypingIndicatorView typingView;
    private String mCategory;

    public void threadShuruKaro(final FragmentActivity fragmentActivity, Context context, View view, final String category) {

        listView = view.findViewById(R.id.pj_news);
        typingView = view.findViewById(R.id.loader);
        mContext = context;
        mCategory = category;


        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (mCategory) {
                    case "headlines":
                        news = parser.parseHeadLines();
                        break;

                    case "state":
                        news = parser.parseCategory(parser.state);
                        break;

                    case "national":
                        news = parser.parseCategory(parser.country);
                        break;

                    case "sports":
                        news = parser.parseCategory(parser.sports);
                        break;

                    case "cinema":
                        news = parser.parseCategory(parser.cinema);
                        break;

                    case "business":
                        news = parser.parseCategory(parser.business);
                        break;
                }


                if (fragmentActivity == null) {
                    return;
                }
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(new ListView_Adapter<News>(mContext, news) {
                            @Override
                            public View getMyView(int i, View view, ViewGroup parent, News news) {
                                if ((view == null) || (view.getTag() == null)) {
                                    view = fragmentActivity.getLayoutInflater().inflate(R.layout.listview_custom_layout, null);
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
                i.putExtra("tag", Constants.prajavani);
                fragmentActivity.startActivity(i);

//                Intent i = new Intent(fragmentActivity, Horizontal_Display_News.class);
//                i.putExtra("tag", Constants.prajavani);
//                i.putExtra("newsObject",news);
//                i.putExtra("position",position);
//                fragmentActivity.startActivity(i);
            }
        });

    }

    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }


}
