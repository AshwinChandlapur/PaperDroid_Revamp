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
public class Tab4 extends Fragment {

    private View view;
    private final String Disclaimer ="Disclaimer\n" +
            "\n" +
            "The information contained in this app is for general information purposes only. We endeavor to keep the information up to date and correct, we make no representations or warranties of any kind, express or implied, about the completeness, accuracy, reliability, suitability or availability with respect to the app or the information, products, services, or related graphics contained on the app for any purpose. Any reliance you place on such information is therefore strictly at your own risk.\n" +
            "News Guru does not own any of the content provided here. The content provided here is used under fair use policy and News Guru would like to credit the original content creators and does'nt intend to infringe copyright."+
            "In no event will we be liable for any loss or damage including without limitation, indirect or consequential loss or damage, or any loss or damage whatsoever arising from loss of data or profits arising out of, or in connection with, the use of this app.\n" +
            "Through this app you are able to link to other apps which are not under the control of NEWS GURU. We have no control over the nature, content and availability of those sites. The inclusion of any links does not necessarily imply a recommendation or endorse the views expressed within them.\n" +
            "Every effort is made to keep the app up and running smoothly. However,NEWS GURU takes no responsibility for, and will not be liable for, the app being temporarily unavailable due to technical issues beyond our control.";

    public Tab4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.all_terms, container, false);
        TextView terms = view.findViewById(R.id.allterms);
        terms.setText(Disclaimer);

        return view;
    }

}
