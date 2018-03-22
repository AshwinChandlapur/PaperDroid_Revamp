package vadeworks.news.paperdroids.VijayaKarnataka.tabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vadeworks.news.paperdroids.Constants;
import vadeworks.news.paperdroids.FirebaseNews;
import vadeworks.paperdroid.R;

public class Tab1_Headlines_VK extends Fragment {

    private final String tag = Constants.vk_headlines;
    private Context context;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.common_headline, container, false);
        ImageView newsIcon = view.findViewById(R.id.newsIcon);
        newsIcon.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.vk));
        init(view);

        FirebaseNews firebaseNews = new FirebaseNews();
        firebaseNews.firebaseNewsFetcher(getActivity(), context, view, tag);


        return view;
    }


    private void init(View v) {
        context = getActivity().getApplicationContext();
    }

}
