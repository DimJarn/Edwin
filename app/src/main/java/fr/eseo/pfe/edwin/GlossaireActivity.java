package fr.eseo.pfe.edwin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GlossaireActivity extends Fragment {

    public static GlossaireActivity newInstance() {
        return (new GlossaireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.glossaire, container, false);
    }
}