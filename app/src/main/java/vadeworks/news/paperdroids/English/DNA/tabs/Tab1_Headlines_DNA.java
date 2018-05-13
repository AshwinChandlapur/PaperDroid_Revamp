package vadeworks.news.paperdroids.English.DNA.tabs;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.FirebaseNews;
import vadeworks.paperdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1_Headlines_DNA extends Fragment {

    private final String tag = Constants.dna_headlines;//TODO:Should Change Accordingly
    private View view;
    private Context context;

    public Tab1_Headlines_DNA() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.common_headline, container, false);
        ImageView newsIcon = view.findViewById(R.id.newsIcon);
        Picasso.with(getContext()).load(R.drawable.dna_head).into(newsIcon);
//        newsIcon.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ht));
        init(view);
        FirebaseNews firebaseNews = new FirebaseNews();
        firebaseNews.firebaseNewsFetcher(getActivity(), context, view, tag);
        return view;
    }

    private void init(View v) {
        context = getActivity().getApplicationContext();
    }

}
