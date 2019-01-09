package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.Glossaire;

/**
 * @author dimitrijarneau
 * Fragment de detail du glossaire
 * Affichage des infos d'un terme choisi
 */
public class GlossaireDetailsFragment extends Fragment implements View.OnClickListener {

    private TextView textView;
    private TextView textView2;
    private int idFiche;

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
     *
     * @param savedInstanceState l'instance saved
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AFFICHE LA VUE DU DETAIL DE GLOSSAIRE
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Methode pour cacher le bouton de recherche issu du fragment précédent
     *
     * @param menu le menu
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        if (item != null) {
            item.setVisible(false);
        }
    }


    /**
     * Création de la vue
     *
     * @param inflater           l'inflater
     * @param container          le container
     * @param savedInstanceState l'instance
     * @return la vue
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.glossaire_details_fragment, container, false);
    }

    /**
     * Methode de creation apres avoir créer l'activité
     *
     * @param savedInstanceState l'instance
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = Objects.requireNonNull(getView()).findViewById(R.id.nom_mot_glossaire);
        textView2 = getView().findViewById(R.id.definition_mot_glossaire);
        setUpButton();

        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("idTerme");
        initData(myInt);
    }

    /**
     * Methode où l'on va initialiser les données pour le fragment
     *
     * @param idTerme l'id du terme selectionné
     */
    private void initData(int idTerme) {
        final Glossaire motGlossaire = EdwinDatabase.getAppDatabase(textView.getContext())
                .glossaireDao().findGlossaireFromId(idTerme);

        textView.setText(motGlossaire.getNomTerme());
        textView2.setText(motGlossaire.getDefinition());
        idFiche = motGlossaire.getRefFiche();

    }

    /**
     * Methode pour initialiser les boutons de la page d'accueil
     */
    private void setUpButton() {
        Button buttonView = Objects.requireNonNull(getView()).findViewById(R.id.buttonView);
        buttonView.setOnClickListener(this);
    }

    /**
     * Methode onClick
     *
     * @param view la vue selectionnée
     */
    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("idFiche", idFiche);
        Fragment fragment = FicheDetailsFragment.newInstance();
        fragment.setArguments(bundle);
        // Très important !!
        //la méthode .addToBackStack permet d'utiliser le bouton retour dans le fragment
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id
                .layout_fragment_glossaire_detail, fragment).commit();

    }
}
