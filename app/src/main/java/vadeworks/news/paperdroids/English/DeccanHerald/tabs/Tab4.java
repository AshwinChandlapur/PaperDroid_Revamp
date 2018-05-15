package vadeworks.news.paperdroids.English.DeccanHerald.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.perf.metrics.AddTrace;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.FirebaseNews;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4 extends Fragment {

    private final String tag = Constants.dh_entertainment;//TODO:Should Change Accordingly
    private View view;
    private Context context;

    public Tab4() {
        // Required empty public constructor
    }


    @Override
    @AddTrace(name = tag, enabled = true)
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
