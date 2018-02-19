package vadeworks.news.paperdroids.Esanje.tabs;


import android.content.Context;
import android.content.Intent;
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
import vadeworks.news.paperdroids.Esanje.Esanje_Parser;
import vadeworks.news.paperdroids.ListView_Adapter;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4_Cinema_ES extends Fragment {

    Context context;
    View view;
    String tag = "cinema";

    public Tab4_Cinema_ES() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.esanje_common_tab, container, false);
        init(view);
        ThreadStarter_ES threadStarter = new ThreadStarter_ES();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);

        return view;
    }


    public void init(View v){
        context = getActivity().getApplicationContext();
    }

}