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


                    vijayakarnataka_url="https://vijaykarnataka.indiatimes.com";
                    try{
                        vijayakarnataka_doc = Jsoup.connect(vijayakarnataka_url).get();
                    }catch (Exception e){
                        Log.d("error","erroro");
                    }

                    sports_link_taker = vijayakarnataka_doc.getElementById("nav10738520").select("a");
                    sports_url = sports_link_taker.attr("href");
                    sports_url = vijayakarnataka_url+sports_url;
                    Log.d("sports-url","sports-url"+sports_url);

                    try{
                        vijayakarnataka_doc = Jsoup.connect(sports_url).get();

                    }catch (Exception e){

                    }



                    //this is of type Document







            }
        }).start();




        return v;
    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vk_news);
    }
}