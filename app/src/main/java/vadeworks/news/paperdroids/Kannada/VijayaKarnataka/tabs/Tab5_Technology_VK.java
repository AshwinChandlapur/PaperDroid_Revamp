package vadeworks.news.paperdroids.Kannada.VijayaKarnataka.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.FirebaseNews;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab5_Technology_VK extends Fragment {

    private final String tag = Constants.vk_technology;
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
        view = inflater.inflate(R.layout.common_tab, container, false);
        init(view);
        FirebaseNews firebaseNews = new FirebaseNews();
        firebaseNews.firebaseNewsFetcher(getActivity(), context, view, tag);
        return view;
    }

    private void init(View v) {
        context = getActivity().getApplicationContext();
    }

}
