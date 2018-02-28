package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vadeworks.news.paperdroids.Constants;
import vadeworks.paperdroid.R;

public class Tab1_Headlines_VK extends Fragment {

    private final String tag = Constants.headlines;
    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.vijayakarnataka_tab1_headlines, container, false);
        init(view);

        ThreadStarter_VK threadStarter = new ThreadStarter_VK();
        threadStarter.threadShuruKaro(getActivity(), context, view, tag);


        return view;
    }


    private void init(View v) {
        context = getActivity().getApplicationContext();
    }

}
