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
public class Tab1 extends Fragment {

    View view;


    String feedback = "Please write in your suggestions to \n\n" +
            "vadeworks@gmail.com\n\n"+
            "We would like to hear from you. :)";


    public Tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.all_terms, container, false);

        TextView terms = view.findViewById(R.id.allterms);
        terms.setText(feedback);
        terms.setGravity(View.TEXT_ALIGNMENT_CENTER);

        return view;
    }

}
