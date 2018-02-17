package vadeworks.news.paperdroids.AsiaNet.tabs;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4_Technology_AN extends Fragment {

    Context context;
    ListView listView;
    ArrayList<News> news = new ArrayList<>();
    String tag = "asianet_technology";
    TypingIndicatorView typingView;


    ViewHolder viewHolder;

    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }


    public Tab4_Technology_AN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.asianet_common_tab, container, false);
        init(view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AsiaNet_Parser parser = new AsiaNet_Parser();
                news = parser.parseCategory(parser.technology);
                        if(getActivity()==null){
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    listView.setAdapter(new ListView_Adapter<News>(context, news) {
                                        @Override
                                        public View getMyView(int i, View view, ViewGroup parent, News news) {
                                            view = layoutinflater(view,news);
                                            return view;
                                        }
                                    });
                            typingView.setVisibility(View.GONE);
                            }
                        });
            }
        }).start();
        listviewOnClick();

        return view;
    }

    public void init(View v){
        listView =  v.findViewById(R.id.an_news);
        context = getActivity().getApplicationContext();
        typingView = (TypingIndicatorView)v.findViewById(R.id.loader);
    }

    public void listviewOnClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), Display_news.class);
                i.putExtra("singleHead",news.get(position).head);
                i.putExtra("singleLink",news.get(position).link);
                i.putExtra("singleImg",news.get(position).imgurl);
                i.putExtra("tag","asianet");
                startActivity(i);
            }
        });
    }

    public View layoutinflater(View view,News news){
        if ((view == null) || (view.getTag() == null)) {
            view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout, null);
            viewHolder = new ViewHolder();
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.news_headline =  view.findViewById(R.id.newsHeadlines);
        viewHolder.news_image =  view.findViewById(R.id.newsImage);
        view.setTag(viewHolder);
        viewHolder.news_headline.setText(news.head);
        if (!news.imgurl.isEmpty()) {
            Picasso.with(context).load(news.imgurl).into(viewHolder.news_image);
        } else {
            viewHolder.news_image.setVisibility(View.GONE);
        }
        return view;
    }

}
