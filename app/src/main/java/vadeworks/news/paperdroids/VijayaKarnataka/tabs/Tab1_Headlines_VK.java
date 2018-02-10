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


/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1_Headlines_VK extends Fragment {

    ListView listView;
    Elements vijayakarnataka_headlines_elem;
    Document vijayakarnataka_doc;
    String vijayakarnataka_url;
    ListView_Adapter listViewAdapter;
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
                    vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_url).get();//this is of type Document
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

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView.setAdapter(new ListView_Adapter<News>(context,news) {
                                    @Override
                                    public View getMyView(int i,View view,ViewGroup parent,News news){
                                        view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
                                        TextView newss = (TextView)view.findViewById(R.id.news);
                                        newss.setText(news.head);
                                        return view;
                                    }
                                });


                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent i = new Intent(getActivity(), Display_news.class);
                                        VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                                        News single = parser.Parse_Headlines(news.get(position));

                                        i.putExtra("singleHead",single.head);
                                        i.putExtra("singleLink",single.link);
                                        i.putExtra("singleContent",single.content);
                                        i.putExtra("singleimg",single.imgurl);
                                        Log.d("single",single.head);
                                        Log.d("single",single.link);
                                        Log.d("single",single.content);
                                        Log.d("single",single.imgurl);
                                        startActivity(i);

//                                        Intent i = new Intent(MainActivity.this, Vertical_News.class);
//                                        i.putExtra("all_headlines",vijayakarnataka_headlines);
//                                        i.putExtra("url", vijayakarnataka_href.get(position));
//                                        i.putExtra("headline",vijayakarnataka_headlines.get(position));
//                                        startActivity(i);
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




//    class CustomAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount(){
//            return vijayakarnataka_headlines_elem.size();
//        }
//
//        @Override
//        public Object getItem(int i){
//            return vijayakarnataka_headlines_elem.get(i);
//        }
//
//        @Override
//        public long getItemId(int i){
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup){
//
////            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//
//            view = getActivity().getLayoutInflater().inflate(R.layout.listview_custom_layout,null);
//            TextView newss = (TextView)view.findViewById(R.id.news);
//            newss.setText(news.get(i).head);
//
//            return view;
//        }
//    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
    }
}
