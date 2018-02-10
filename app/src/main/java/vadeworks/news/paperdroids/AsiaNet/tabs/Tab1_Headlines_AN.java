package vadeworks.news.paperdroids.AsiaNet.tabs;


import android.content.Context;
import android.content.Intent;
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

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1_Headlines_AN extends Fragment {

    ListView listView;
    Elements asianet_headlines_elem;
    Document asianet_doc;
    String asianet_url;
    ListView_Adapter listViewAdapter;
    Context context;

    ArrayList<News> news = new ArrayList<News>();


    public Tab1_Headlines_AN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.asianet_tab1_headlines, container, false);
        context = getActivity().getApplicationContext();
        init(view);

        //For VijayaKarnataka Main Headlines//
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Run", "run: Start Running");
                try {
                    asianet_url ="http://kannada.asianetnews.com/news";//this is a string
                    asianet_doc = Jsoup.connect(asianet_url).get();//this is of type Document
                    Elements asianet_headlines_elem = asianet_doc.getElementsByClass("col-sm-4 col-xs-6 cl-text-bg").select("a");

                    int i;
                    for(i=0; i< asianet_headlines_elem.size(); i++){

                        String link = asianet_headlines_elem.get(i).attr("href");
                        String headline = asianet_headlines_elem.get(i).select("img:lt(1)").attr("title");
                        String img_url = asianet_headlines_elem.get(i).select("img:lt(1)").attr("data-original");
                        //lt(n) --->elements whose sibling index is less than n

                        news.add(new News(headline,link,img_url));
                        news.get(i).showNews();
                    } Log.d("news-info","size is"+news.size());

                    for(i=0; i< asianet_headlines_elem.size(); i++){

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView.setAdapter(new ListView_Adapter<News>(context,news) {
                                    @Override
                                    public View getMyView(int i,View view,ViewGroup parent,News news){
                                        view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
                                        TextView news_headline = (TextView)view.findViewById(R.id.newsHeadlines);
                                        ImageView news_image = (ImageView)view.findViewById(R.id.newsImage);
                                        news_headline.setText(news.head);
                                        Picasso.with(context).load(news.imgurl).into(news_image);
                                        return view;
                                    }
                                });


//                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        Intent i = new Intent(getActivity(), Display_news.class);
//                                        VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
//                                        News single = parser.Parse_Headlines(news.get(position));
//
//                                        i.putExtra("singleHead",single.head);
//                                        i.putExtra("singleLink",single.link);
//                                        i.putExtra("singleContent",single.content);
//                                        i.putExtra("singleimg",single.imgurl);
//                                        Log.d("single",single.head);
//                                        Log.d("single",single.link);
//                                        Log.d("single",single.content);
//                                        Log.d("single",single.imgurl);
//                                        startActivity(i);
//
////                                        Intent i = new Intent(MainActivity.this, Vertical_News.class);
////                                        i.putExtra("all_headlines",vijayakarnataka_headlines);
////                                        i.putExtra("url", vijayakarnataka_href.get(position));
////                                        i.putExtra("headline",vijayakarnataka_headlines.get(position));
////                                        startActivity(i);
//                                    }
//                                });
                            }
                        });

                    }

                } catch (IOException e) {

                }
            }
        }).start();









        return view;
    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.an_news);
    }



}