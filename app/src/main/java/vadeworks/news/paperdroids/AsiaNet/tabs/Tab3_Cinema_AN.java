package vadeworks.news.paperdroids.AsiaNet.tabs;


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
public class Tab3_Cinema_AN extends Fragment {

    private View view;
    private Context context;
    private final String tag = "cinema";


    public Tab3_Cinema_AN() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.asianet_common_tab, container, false);
        init(view);
        ThreadStarter_AN threadStarter = new ThreadStarter_AN();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);
        return view;
    }

    private void init(View v){
        context = getActivity().getApplicationContext();
    }


}
