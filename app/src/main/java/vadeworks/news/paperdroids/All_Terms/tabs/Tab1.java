package vadeworks.news.paperdroids.All_Terms.tabs;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        view= inflater.inflate(R.layout.feedback, container, false);


        Button suggestions = view.findViewById(R.id.suggestions);
        Button giveusa5 = view.findViewById(R.id.rateus);

        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "vadeworks@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Suggestions to improve "+ getString(R.string.app_name));
                startActivity(intent);
            }
        });


        giveusa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getActivity().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });



        return view;
    }

}
