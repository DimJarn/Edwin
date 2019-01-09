package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;
import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;
import fr.eseo.pfe.edwin.utilitaires.TinyDB;

public class FicheDetailsFragment extends Fragment {
    public static final String LISTE_FICHE_INFORMATIVE_ID_FICHE = "listeFicheInformativeIdFiche";

    private FicheInformative ficheInformative;

    /* LAST VERSION
    private ListView mListView;
    private ExpandableListView listView;
    private ExpandableAdapter listAdapter;
    private List<String> listDataheader;
    private HashMap<String, List<String>> listHash;
    */

    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new FicheDetailsFragment());
    }

    /**
     * Creation
     *
     * @param savedInstanceState l'instance sauvegardée
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AFFICHE LA VUE DU DETAIL DE GLOSSAIRE
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Création de la vue
     *
     * @param inflater           le layout
     * @param container          le container
     * @param savedInstanceState l'instance sauvegardée
     * @return la vue
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.fiche_details_fragment, container, false);
    }

    /**
     * creation après
     *
     * @param savedInstanceState l'instance
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //listView = (ExpandableListView) getView().findViewById(R.id.expand);

        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("idFiche");

        initData(myInt);

        //listAdapter = new ExpandableAdapter(getContext(), listDataheader, listHash);
        //listView.setAdapter(listAdapter);

        //setButton();
    }

    private void initData(int idFiche) {

        ficheInformative = EdwinDatabase.getAppDatabase(getContext())
                .ficheInformativeDao().findFicheInformativeFromId(idFiche);
        TextView textViewTitre = getView().findViewById(R.id.nom_operation);
        textViewTitre.setText(ficheInformative.getNomOperation());

        ContenuFiche listeFiches = EdwinDatabase.getAppDatabase(getContext())
                .contenuFicheDao().findContenuFicheFromId(idFiche);

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv = getView().findViewById(R.id
                .expand_text_viewRappelAnatomique);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv.setText(listeFiches.getRappelAnatomique());

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = getView().findViewById(R.id
                .expand_text_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(listeFiches.getMaladie());


        ExpandableTextView expTvPourquoiOperer = getView().findViewById(R.id
                .expand_text_view_PourquoiOpération);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvPourquoiOperer.setText(listeFiches.getRisquesMaladie());


        ExpandableTextView expTvPrincipeIntervention = getView().findViewById
                (R.id.expand_text_view_PrincipesIntervention);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvPrincipeIntervention.setText(listeFiches.getPrincipe());

        ExpandableTextView expTvTechniquesOperatoires = getView().findViewById
                (R.id.expand_text_view_TechniquesOperatoires);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesOperatoires.setText(listeFiches.getTechnique());


        ExpandableTextView expTvTechniquesSuitesHabituelles = getView()
                .findViewById(R.id.expand_text_view_SuitesHabituelles);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesSuitesHabituelles.setText(listeFiches.getSuites());


        ExpandableTextView expTvTechniquesRisquesLies = getView()
                .findViewById(R.id.expand_text_view_Risquesliés);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesRisquesLies.setText(listeFiches.getRisquesOperation());


        ExpandableTextView expTvTechniquesSuivi = getView()
                .findViewById(R.id.expand_text_view_Suivi);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesSuivi.setText(listeFiches.getSuivi());


        //LAST VERSION


 /*       listDataheader = new ArrayList<>();
        listHash = new HashMap<>();


        listDataheader.add("Pourquoi vous faire opérer - alternatives ?");
        listDataheader.add("Principes de l'intervention");
        listDataheader.add("Techniques opératoires");
        listDataheader.add("Suites habituelles");
        listDataheader.add("Risques liés");
        listDataheader.add("Suivi post-opératoire");

        List<String> pourquoi = new ArrayList<>();
        pourquoi.add(listeFiches.getRisquesMaladie());

        List<String> principes = new ArrayList<>();
        principes.add(listeFiches.getPrincipe());

        List<String> techniques = new ArrayList<>();
        techniques.add(listeFiches.getTechnique());

        List<String> suites = new ArrayList<>();
        suites.add(listeFiches.getSuites());

        List<String> risques = new ArrayList<>();
        risques.add(listeFiches.getRisquesOperation());

        List<String> suivi = new ArrayList<>();
        suivi.add(listeFiches.getSuivi());

        listHash.put(listDataheader.get(0), pourquoi);
        listHash.put(listDataheader.get(1), principes);
        listHash.put(listDataheader.get(2), techniques);
        listHash.put(listDataheader.get(3), suites);
        listHash.put(listDataheader.get(4), risques);
        listHash.put(listDataheader.get(5), suivi);
*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorites_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        TinyDB tinydb = new TinyDB(getContext());
        List<Integer> listeIdsFichesFavorites = tinydb.getListInt(LISTE_FICHE_INFORMATIVE_ID_FICHE);
        if (listeIdsFichesFavorites.contains(ficheInformative.getIdFiche())) {
            menu.findItem(R.id.item1).getIcon().mutate().setColorFilter(getResources().getColor
                    (R.color.Gold), PorterDuff.Mode.SRC_IN);
        } else {
            menu.findItem(R.id.item1).setChecked(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                TinyDB tinydb2 = new TinyDB(getContext());
                ArrayList<Integer> allIdFicheStocked2 = tinydb2.getListInt
                        (LISTE_FICHE_INFORMATIVE_ID_FICHE);
                if (allIdFicheStocked2.contains(ficheInformative.getIdFiche())) {
                    allIdFicheStocked2.remove(Integer.valueOf(ficheInformative.getIdFiche()));
                    tinydb2.putListInt(LISTE_FICHE_INFORMATIVE_ID_FICHE, allIdFicheStocked2);
                    Toasty.warning(getContext(), "Fiche supprimée des favoris !", Toast
                            .LENGTH_SHORT, true).show();
                } else {
                    allIdFicheStocked2.add(ficheInformative.getIdFiche());
                    Set set2 = new HashSet();
                    set2.addAll(allIdFicheStocked2);
                    ArrayList<Integer> uniqueIdStocked2 = new ArrayList<>(set2);
                    tinydb2.putListInt(LISTE_FICHE_INFORMATIVE_ID_FICHE, uniqueIdStocked2);

                    Toasty.success(getContext(), "Fiche ajoutée aux favoris !", Toast
                            .LENGTH_SHORT, true).show();
                }
                FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.detach(this).attach(this).commit();
                return true;
            default:
                Log.d("FicheDetalsFragment", "Erreur");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
