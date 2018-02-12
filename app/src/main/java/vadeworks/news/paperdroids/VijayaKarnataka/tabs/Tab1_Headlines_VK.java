package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class Tab1_Headlines_VK extends Fragment {

    ListView listView;
    Elements vijayakarnataka_headlines_elem;
    Document vijayakarnataka_doc;
    String vijayakarnataka_url;
    Context context;

    ArrayList<News> news = new ArrayList<News>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vijayakarnataka_tab1_headlines,container,false);
        init(v);

        context = getActivity().getApplicationContext();


        //For VijayaKarnataka Main Headlines//
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Run", "run: Start Running");
                try {
                    vijayakarnataka_url="https://vijaykarnataka.indiatimes.com/";//this is a string
                    Log.d("timestamp","timestamp Headlines Start");
                    vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_url).get();//this is of type Document
                    Log.d("timestamp","timestamp Headlines Dome");
                    vijayakarnataka_headlines_elem = vijayakarnataka_doc.getElementsByClass("other_main_news1").select("ul").select("li").select("a");//this has the headline
                    //vijayakarnataka_headlines_elem is of type Elements

                    int i;
                    for(i=0;i<vijayakarnataka_headlines_elem.size();i++){

                        String link = vijayakarnataka_url+vijayakarnataka_headlines_elem.get(i).attr("href");
                        String headline = vijayakarnataka_headlines_elem.get(i).text();
                        news.add(new News(headline,link));
                        news.get(i).showNews();
                    }

                    for(i=0;i<vijayakarnataka_headlines_elem.size();i++){

//                        try {
//                            Thread.sleep(100);
//                        } catch (InterruptedException e) {
//                            Toast.makeText(getActivity().getApplicationContext(), "Default Signature                         Fail", Toast.LENGTH_LONG).show();
//                            e.printStackTrace();
//                        }

                        // here you check the value of getActivity() and break up if needed
                        if(getActivity() == null)
                            return;

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView.setAdapter(new ListView_Adapter<News>(context,news) {
                                    @Override
                                    public View getMyView(int i,View view,ViewGroup parent,News news){
                                        view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
                                        TextView news_Headline = (TextView)view.findViewById(R.id.newsHeadlines);
                                        news_Headline.setText(news.head);
                                        ImageView news_image = (ImageView)view.findViewById(R.id.newsImage);
                                        news_image.setVisibility(View.GONE);
                                        return view;
                                    }
                                });


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent i = new Intent(getActivity(), Display_news.class);
//                                        VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
//                                        News single = parser.Parse_For_Content(news.get(position));
                                        i.putExtra("singleHead",news.get(position).head);
                                        i.putExtra("singleLink",news.get(position).link);
//                                        i.putExtra("singleContent",single.content);
//                                        i.putExtra("singleImg",single.imgurl);
                                        Log.d("Parser single","parser"+news.get(position).head);
                                        Log.d("Parser single","parser"+news.get(position).link);
//                                        Log.d("Parser single","parser"+single.content);
//                                        Log.d("Parser single","parser"+single.imgurl);
                                        startActivity(i);
                                        Log.d("timestamp","timestamp of StartActivity");
                                    }
                                });
                            }
                        });

                    }

                } catch (IOException e) {

                }
            }
        }).start();
// VijayaKarnataka Main Headlines Ends Here

        return v;
    }


    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
    }
}
