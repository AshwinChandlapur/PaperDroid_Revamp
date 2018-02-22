package vadeworks.news.paperdroids.VijayaKarnataka.tabs;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vadeworks.paperdroid.R;


public class Tab2_Sports_VK extends Fragment {

    Context context;
    View view;
    String tag = "sports";

    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vijayakarnataka_common_tab,container,false);
        init(view);


        ThreadStarter_VK threadStarter = new ThreadStarter_VK();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);
        return view;
    }

    public void init(View v){
        context = getActivity().getApplicationContext();
    }


}