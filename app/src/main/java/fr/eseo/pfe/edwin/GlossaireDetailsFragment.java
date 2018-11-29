package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.Glossaire;

/**
 * @author dimitrijarneau
 * Fragment de detail du glossaire
 * Affichage des infos d'un terme choisi
 */
public class GlossaireDetailsFragment extends Fragment {

    private TextView textView;
    private TextView textView2;
    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new GlossaireDetailsFragment());
    }


    /**
     * Creation
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AFFICHE LA VUE DU DETAIL DE GLOSSAIRE
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
/*
    *//**
     * Methode de creation barre de recherche
     *
     * @param menu
     * @param inflater
     *//*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }*/

    /**
     * Methode pour cacher le bouton de recherche issu du fragment précédent
     * @param menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.action_search);
        item.setVisible(false);
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
        View view = inflater.inflate(R.layout.glossaire_details_fragment, container, false);
        return view;
    }

    /**
     * creation après
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView) getView().findViewById(R.id.nom_mot_glossaire);
        textView2 = (TextView) getView().findViewById(R.id.definition_mot_glossaire);

        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("idTerme");
        initData(myInt);
    }

    private void initData(int idTerme) {
        Glossaire motGlossaire = EdwinDatabase.getAppDatabase(textView.getContext()).glossaireDao().findGlossaireFromId(idTerme);

        textView.setText(motGlossaire.getNomTerme());
        textView2.setText(motGlossaire.getDefinition());

    }
}
