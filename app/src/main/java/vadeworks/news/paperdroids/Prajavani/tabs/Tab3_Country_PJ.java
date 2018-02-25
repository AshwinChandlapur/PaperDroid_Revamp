package vadeworks.news.paperdroids.Prajavani.tabs;


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
public class Tab3_Country_PJ extends Fragment {

    private final String tag = "national";
    private Context context;
    private View view;

    public Tab3_Country_PJ() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.prajavaani_common_tab, container, false);
        init(view);
        ThreadStater_PJ threadStarter = new ThreadStater_PJ();
        threadStarter.threadShuruKaro(getActivity(), context, view, tag);


        return view;
    }

    private void init(View v) {
        context = getActivity().getApplicationContext();
    }


}
