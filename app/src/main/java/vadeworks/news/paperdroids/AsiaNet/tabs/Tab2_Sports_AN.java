package vadeworks.news.paperdroids.AsiaNet.tabs;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2_Sports_AN extends Fragment {

    Context context;
    ListView listView;
    ArrayList<News> news = new ArrayList<News>();
    String tag = "asianet_sports";
    ViewHolder viewHolder;



    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }



    public Tab2_Sports_AN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.asianet_tab2_sports, container, false);
        init(view);


        new Thread(new Runnable() {
            @Override
            public void run() {

                AsiaNet_Parser parser = new AsiaNet_Parser();
                news = parser.parseCategory(tag);

                if(getActivity()==null){
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int i;
                        for(i=0; i< news.size(); i++){
                            listView.setAdapter(new ListView_Adapter<News>(context,news) {
                                @Override
                                public View getMyView(int i,View view,ViewGroup parent,News news){
                                    if((view == null) || (view.getTag() == null))
                                    {view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
                                        viewHolder = new ViewHolder();}
                                    else{
                                        viewHolder = (ViewHolder) view.getTag();}

                                    viewHolder.news_headline = (TextView)view.findViewById(R.id.newsHeadlines);
                                    viewHolder.news_image = (ImageView)view.findViewById(R.id.newsImage);
                                    viewHolder.news_headline.setText(news.head);
                                    view.setTag(viewHolder);
                                    if(!news.imgurl.isEmpty()) {
                                        Picasso.with(context).load(news.imgurl).into(viewHolder.news_image);
                                    }else{
                                        viewHolder.news_image.setVisibility(View.GONE);
                                    }
                                    return view;
                                }
                            });
                        }


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
                });
            }
        }).start();


        return view;
    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.an_news);
        context = getActivity().getApplicationContext();
    }






}
