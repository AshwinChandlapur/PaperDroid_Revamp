package vadeworks.news.paperdroids.VijayaVaani.tabs;


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
public class Tab1_Headlines_VV extends Fragment {

    private Context context;
    private View view;
    private final String tag = "headlines";

    public Tab1_Headlines_VV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.vijayavaani_tab1_headlines, container, false);
        init(view);
        ThreadStarter_VV threadStarter = new ThreadStarter_VV();
        threadStarter.threadShuruKaro(getActivity(),context,view,tag);


        return view;
    }







    private void init(View v){
        context = getActivity().getApplicationContext();
    }

}
