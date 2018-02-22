package vadeworks.news.paperdroids.VijayaKarnataka.tabs;


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
public class Tab4_Lifestyle_VK extends Fragment {


    Context context;
    View view;
    String tag = "lifestyle";

    public Tab4_Lifestyle_VK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.vijayakarnataka_common_tab, container, false);
        init(view);
        ThreadStarter_VK threadStarter = new ThreadStarter_VK();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);



        return view;
    }
    public void init(View v){
        context = getActivity().getApplicationContext();
    }

}
