package vadeworks.news.paperdroids.VijayaKarnataka.tabs;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.paperdroid.R;


public class Tab2_Sports_VK extends Fragment {

    ListView listView;
    Context context;
    String vijayakarnataka_url,sports_url;
    org.jsoup.nodes.Document vijayakarnataka_doc ;
    Elements sports_link_taker,sports_link_takers,mains;

    ArrayList<News> news = new ArrayList<News>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vijayakarnataka_tab2_sports,container,false);
        init(v);
        context = getActivity().getApplicationContext();

        new Thread(new Runnable() {
            @Override
            public void run() {

                VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                news = parser.parseCategory("sports");
                int i;

                for(i=0;i<news.size();i++){

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
                                    if(!news.imgurl.isEmpty())
                                    {
                                        Picasso.with(context).load(news.imgurl).into(news_image);
                                    }else{
                                        news_image.setVisibility(View.GONE);
                                    }

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
                                    Log.d("link on Click","link on Click  "+news.get(position).link );
                                    startActivity(i);
                                }
                            });
                        }
                    });

                }

            }
        }).start();




        return v;
    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
    }
}