package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.analytics.FirebaseAnalytics;

import vadeworks.paperdroid.R;

public class Tab1_Headlines_VK extends Fragment {

    Context context;
    View view;
    String tag = "headlines";
    private FirebaseAnalytics mFirebaseAnalytics;
    Bundle params = new Bundle();
    String category_clicked;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vijayakarnataka_tab1_headlines,container,false);
        init(view);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        category_clicked = getResources().getString(R.string.headlines_vk_en);
        mFirebaseAnalytics.logEvent(category_clicked,params);

        ThreadStarter_VK threadStarter = new ThreadStarter_VK();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);


        return view;
    }


    public void init(View v){
        context = getActivity().getApplicationContext();
    }

}
