package vadeworks.news.paperdroids.VijayaVaani.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import vadeworks.news.paperdroids.News;
import vadeworks.news.paperdroids.Prajavani.tabs.Tab1_Headlines_PJ;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4 extends Fragment {


    ListView listView;
    Context context;
    ArrayList<News> news = new ArrayList<News>();
    Tab1_Headlines_VV.ViewHolder viewHolder;

    static class ViewHolder {
        static TextView news_headline;
        static ImageView news_image;
    }


    public Tab4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.vijayavaani_tab4, container, false);
        init(v);

        return v;
    }

    public void init(View v){
        listView = (ListView) v.findViewById(R.id.vv_news);
        context = getActivity().getApplicationContext();
    }

}
