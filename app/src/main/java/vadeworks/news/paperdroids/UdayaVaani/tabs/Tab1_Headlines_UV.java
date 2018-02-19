package vadeworks.news.paperdroids.UdayaVaani.tabs;


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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vadeworks.news.paperdroids.AsiaNet.AsiaNet_Parser;
import vadeworks.news.paperdroids.AsiaNet.tabs.Tab1_Headlines_AN;
import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.tabs.ThreadStater_PJ;
import vadeworks.news.paperdroids.UdayaVaani.Udayavaani_Parser;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1_Headlines_UV extends Fragment {

    Context context;
    View view;
    String tag = "headlines";


    public Tab1_Headlines_UV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.udayavaani_tab1_headlines, container, false);
        init(view);
        ThreadStarter_UV threadStarter = new ThreadStarter_UV();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);


        return view;
    }

    public void init(View v){
        context = getActivity().getApplicationContext();
    }

}
