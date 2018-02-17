package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.tabs.Tab1_Headlines_AN;
import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.paperdroid.R;

public class Tab1_Headlines_VK extends Fragment {

    ListView listView;
    Context context;
    String tag = "vk_headlines";

    ViewHolder viewHolder;
    TypingIndicatorView typingView;
    SwipeRefreshLayout mySwipreRefreshLayout;

    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }

    ArrayList<News> news = new ArrayList<News>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vijayakarnataka_tab1_headlines,container,false);
        init(v);

        //For VijayaKarnataka Main Headlines//
        new Thread(new Runnable() {
            @Override
            public void run() {

                VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                news = parser.parseHeadLines();
                        if(getActivity() == null)
                            return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    listView.setAdapter(new ListView_Adapter<News>(context,news) {
                                        @Override
                                        public View getMyView(int i,View view,ViewGroup parent,News news){
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



        return v;
    }


    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
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
                i.putExtra("tag","vijayakarnataka");
                startActivity(i);
            }
        });
    }


    public void threadStarter(){


    }

    public View layoutinflater(View view,News news){
        if((view == null)|| (view.getTag() == null))
        {
            view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
            viewHolder = new ViewHolder();
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.news_headline = (TextView)view.findViewById(R.id.newsHeadlines);
        viewHolder.news_image = (ImageView)view.findViewById(R.id.newsImage);
        view.setTag(viewHolder);
        viewHolder.news_headline.setText(news.head);
        viewHolder.news_image.setVisibility(View.GONE);

        return view;
    }

}
