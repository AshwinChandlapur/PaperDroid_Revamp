package vadeworks.news.paperdroids.VijayaKarnataka.tabs;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4_Lifestyle_VK extends Fragment {


    ListView listView;
    Context context;
    String vijayakarnataka_url,sports_url;
    org.jsoup.nodes.Document vijayakarnataka_doc ;
    Elements sports_link_taker,sports_link_takers;

    ArrayList<News> news = new ArrayList<News>();


    public Tab4_Lifestyle_VK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.vijayakarnataka_tab4_lifestyle, container, false);

        init(v);
        context = getActivity().getApplicationContext();

        new Thread(new Runnable() {
            @Override
            public void run() {


                vijayakarnataka_url="https://vijaykarnataka.indiatimes.com";
                try{
                    Log.d("timestamp","timestamp Lifestyle Start");
                    vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_url).get();
                    Log.d("timestamp","timestamp Lifestyle Done");
                }catch (Exception e){
                    Log.d("error","error");
                }

                sports_link_taker = vijayakarnataka_doc.getElementById("nav57869229").select("a"); // Nav id of Lifestyle
                sports_url = sports_link_taker.attr("href");
                sports_url = vijayakarnataka_url+sports_url;
//                    Log.d("sports-url","sports-url"+sports_url);

                try{
                    vijayakarnataka_doc = Jsoup.connect(sports_url).get();
                }catch (Exception e){
                    Log.d("error","error");
                }

                sports_link_taker = vijayakarnataka_doc.getElementsByClass("dvlstimgs").select("a");
                Log.d("sports-url","sports-size"+sports_link_taker.size());
                int i;
                for(i=0;i<sports_link_taker.size();i++){
                    String link =sports_link_taker.get(i).attr("href");
                    link = vijayakarnataka_url+link;
                    Log.d("sports-url","sports-link "+link);


                    sports_link_takers = vijayakarnataka_doc.getElementsByClass("dvlstimgs").select("a").select("img");
                    String imgurl = sports_link_takers.get(i).attr("src");
                    imgurl = vijayakarnataka_url+imgurl;
                    Log.d("sports-url","sports-image "+imgurl);


                    String headline = sports_link_takers.get(i).attr("title");
                    Log.d("sports-url","sports-headline "+headline);

                    news.add(new News(headline,link,imgurl));

                }


                for(i=0;i<sports_link_taker.size();i++){



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
                                    VijayaKarnataka_Parser parser = new VijayaKarnataka_Parser();
                                    News single = parser.Parse_For_Content(news.get(position));

                                    i.putExtra("singleHead",single.head);
                                    i.putExtra("singleLink",single.link);
                                    i.putExtra("singleContent",single.content);
                                    i.putExtra("singleImg",single.imgurl);
                                    Log.d("Parser single","parser"+news.get(position).head);
                                    Log.d("Parser single","parser"+news.get(position).link);
                                    Log.d("Parser single","parser"+single.content);
                                    Log.d("Parser single","parser"+single.imgurl);
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
