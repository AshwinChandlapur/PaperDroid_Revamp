package vadeworks.news.paperdroids.English.DeccanHerald.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.perf.metrics.AddTrace;

import java.util.ArrayList;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.FirebaseNews;
import vadeworks.news.paperdroids.News;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {


    private final String tag = Constants.dh_headlines;//TODO:Should Change Accordingly
    private View view;
    private Context context;
    private ArrayList<News> newsArrayList;

    public Tab1() {
        // Required empty public constructor
    }

    @Override
    @AddTrace(name = tag, enabled = true)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.common_headline, container, false);
        ImageView newsIcon = view.findViewById(R.id.newsIcon);
        newsIcon.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.deccan));
        init(view);
        FirebaseNews firebaseNews = new FirebaseNews();
        firebaseNews.firebaseNewsFetcher(getActivity(), context, view, tag);
//        Speakerbox speakerbox = new Speakerbox(getActivity().getApplication());
//        speakerbox.play("Today's Headlines are:");
//        speakerbox.play("Sharif questions Pakistan policy to allow ‘non-state actors’ to cross border and kill Mumbai people ");


        return view;
    }

    private void init(View v) {
        context = getActivity().getApplicationContext();
    }

}
