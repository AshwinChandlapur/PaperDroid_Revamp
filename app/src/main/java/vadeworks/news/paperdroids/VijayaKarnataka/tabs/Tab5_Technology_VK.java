package vadeworks.news.paperdroids.VijayaKarnataka.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import vadeworks.news.paperdroids.Constants;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab5_Technology_VK extends Fragment {

    private final String tag = Constants.technology;
    Bundle params = new Bundle();
    String category_clicked;
    private Context context;
    private View view;
    private FirebaseAnalytics mFirebaseAnalytics;

    public Tab5_Technology_VK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.vijayakarnataka_common_tab, container, false);
        init(view);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        category_clicked = getResources().getString(R.string.toolbar_title_home_vk_en+R.string.technology_kn_en);
//        mFirebaseAnalytics.logEvent(category_clicked,params);

        ThreadStarter_VK threadStarter = new ThreadStarter_VK();
        threadStarter.threadShuruKaro(getActivity(), context, view, tag);
        return view;
    }

    private void init(View v) {
        context = getActivity().getApplicationContext();
    }

}
