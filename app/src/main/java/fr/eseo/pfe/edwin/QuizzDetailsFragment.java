package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuizzDetailsFragment extends ListFragment {

    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new QuizzDetailsFragment());
    }


    /**
     * Creation
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AFFICHE LA VUE DU DETAIL DE GLOSSAIRE
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * creation après
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Création de la vue
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_details_fragment, container, false);
        return view;
    }
}
