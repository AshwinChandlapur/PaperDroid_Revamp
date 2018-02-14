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

import com.bluehomestudio.progresswindow.ProgressWindow;
import com.bluehomestudio.progresswindow.ProgressWindowConfiguration;
import com.squareup.picasso.Picasso;

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
    private ProgressWindow progressWindow;

    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }

    ArrayList<News> news = new ArrayList<News>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vijayakarnataka_tab1_headlines,container,false);
        init(v);

        progressConfigurations();
        showProgress();
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
                                            if((view == null)|| (view.getTag() == null))
                                            {
                                                view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
                                                viewHolder = new ViewHolder();
                                                viewHolder.news_headline = (TextView)view.findViewById(R.id.newsHeadlines);
                                                viewHolder.news_image = (ImageView)view.findViewById(R.id.newsImage);
                                            }else{
                                                viewHolder = (ViewHolder) view.getTag();
                                            }

                                            viewHolder.news_headline.setText(news.head);
                                            viewHolder.news_image.setVisibility(View.GONE);
                                            return view;
                                        }
                                    });

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent i = new Intent(getActivity(), Display_news.class);
                                        i.putExtra("singleHead",news.get(position).head);
                                        i.putExtra("singleLink",news.get(position).link);
                                        i.putExtra("tag","vijayakarnataka");
                                        startActivity(i);
                                        Log.d("timestamp","timestamp of StartActivity");
                                    }
                                });

                                hideProgress();

                            }
                        });
            }
        }).start();
// VijayaKarnataka Main Headlines Ends Here

        return v;
    }


    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
        context = getActivity().getApplicationContext();
    }


    private void progressConfigurations(){
        progressWindow = ProgressWindow.getInstance(context);
        ProgressWindowConfiguration progressWindowConfiguration = new ProgressWindowConfiguration();
        progressWindowConfiguration.backgroundColor = Color.parseColor("#32000000") ;
        progressWindowConfiguration.progressColor = Color.WHITE ;
        progressWindow.setConfiguration(progressWindowConfiguration);
    }

    public void showProgress(){
        progressWindow.showProgress();
    }


    public void hideProgress(){
        progressWindow.hideProgress();
    }
}
