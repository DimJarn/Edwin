package fr.eseo.pfe.edwin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AccueilActivity extends Fragment implements View.OnClickListener {

    public static AccueilActivity newInstance() {
        return (new AccueilActivity());
    }

    Fragment AideActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accueil, container, false);

        Button buttonHelp = (Button) view.findViewById(R.id.buttonNeedHelp);
        buttonHelp.setOnClickListener(this);

        Button buttonYourOperation = (Button) view.findViewById(R.id.buttonYourOperation);
        buttonYourOperation.setOnClickListener(this);

        Button buttonMoreInfo = (Button) view.findViewById(R.id.buttonMoreInfo);
        buttonMoreInfo.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYourOperation:
                //Start activity one
                System.out.println("BUGGgG");
                break;
            case R.id.buttonNeedHelp:
                System.out.println("TEST");
                Fragment AideActivity = new AideActivity();
                break;
            case R.id.buttonMoreInfo:
                System.out.println("XXVVVVVMMMM");
                break;
            // Do this for all buttons.
        }
    }
}