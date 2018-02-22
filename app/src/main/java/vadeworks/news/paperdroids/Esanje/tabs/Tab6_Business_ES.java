package vadeworks.news.paperdroids.Esanje.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */

public class Tab6_Business_ES extends Fragment {

    Context context;
    View view;
    String tag = "business";


    public Tab6_Business_ES() {
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