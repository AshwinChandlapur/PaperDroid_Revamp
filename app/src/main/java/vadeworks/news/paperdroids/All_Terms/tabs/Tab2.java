package vadeworks.news.paperdroids.All_Terms.tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import vadeworks.paperdroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {


    public Tab2() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.all_terms, container, false);

        return view;
    }

}
