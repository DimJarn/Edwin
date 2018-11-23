package fr.eseo.pfe.edwin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AideActivity extends Fragment {

    public static AideActivity newInstance() {
        return (new AideActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aide_activity, container, false);
    }
}