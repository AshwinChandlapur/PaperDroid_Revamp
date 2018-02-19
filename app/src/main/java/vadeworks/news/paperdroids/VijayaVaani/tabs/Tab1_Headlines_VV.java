package vadeworks.news.paperdroids.VijayaVaani.tabs;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.udevel.widgetlab.TypingIndicatorView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.Display_news;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.tabs.Tab1_Headlines_PJ;
import vadeworks.news.paperdroids.VijayaKarnataka.VijayaKarnataka_Parser;
import vadeworks.news.paperdroids.VijayaKarnataka.tabs.ThreadStarter_VK;
import vadeworks.news.paperdroids.VijayaVaani.Vijayavaani_Parser;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1_Headlines_VV extends Fragment {

    Context context;
    View view;
    String tag = "headlines";

    public Tab1_Headlines_VV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.vijayavaani_tab1_headlines, container, false);
        init(view);
        ThreadStarter_VV threadStarter = new ThreadStarter_VV();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);


        return view;
    }







    public void init(View v){
        context = getActivity().getApplicationContext();
    }

}
