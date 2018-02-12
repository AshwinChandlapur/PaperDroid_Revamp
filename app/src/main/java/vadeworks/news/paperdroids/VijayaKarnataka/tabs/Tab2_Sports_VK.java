package vadeworks.news.paperdroids.VijayaKarnataka.tabs;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import vadeworks.paperdroid.R;


public class Tab2_Sports_VK extends Fragment {

    ListView listView;
    Context context;
    String vijayakarnataka_url,sports_url;
    org.jsoup.nodes.Document vijayakarnataka_doc ;
    Elements sports_link_taker;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vijayakarnataka_tab_2,container,false);
        init(v);
        context = getActivity().getApplicationContext();
        Log.d("error","erroro");


        new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    vijayakarnataka_url="https://vijaykarnataka.indiatimes.com";
                    vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_url).get();
                    sports_link_taker = vijayakarnataka_doc.getElementsByClass("topnav").select("li:eq(4)").select("a");
                    sports_url = sports_link_taker.attr("href");

                    Log.d("sports-url","sports "+sports_url);
                    //this is of type Document

                }catch (Exception e){
                    Log.d("error","error");
                }



            }
        }).start();




        return v;
    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
    }
}