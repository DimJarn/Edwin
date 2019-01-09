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
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.dmoral.toasty.Toasty;
import fr.eseo.pfe.edwin.Util.ExpandableAdapter;
import fr.eseo.pfe.edwin.Util.TinyDB;
import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;

public class FicheDetailsFragment extends Fragment {
    public static final String LISTE_FICHE_INFORMATIVE_ID_FICHE = "listeFicheInformativeIdFiche";
    View.OnClickListener btnClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag();
            Toast.makeText(getContext(), "clicked button" + tag + " / ID", Toast.LENGTH_SHORT)
                    .show();

            Bundle bundle = new Bundle();
            bundle.putInt("idTerme", (Integer) tag);

            Fragment fragment = GlossaireDetailsFragment.newInstance();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id
                    .layoutFicheDetail, fragment).commit();
        }
    };
    private ExpandableListView listView;
    private ExpandableAdapter listAdapter;
    private List<String> listDataheader;
    private HashMap<String, List<String>> listHash;
    private TextView textViewTitre;
    private TextView textViewTitrePourquoiOpération;

    private FicheInformative ficheInformative;
    private ListView mListView;

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
     * @param savedInstanceState
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
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fiche_details_fragment, container, false);
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
        textViewTitre = (TextView) getView().findViewById(R.id.nom_operation);
        textViewTitre.setText(ficheInformative.getNomOperation());

        ContenuFiche listeFiches = EdwinDatabase.getAppDatabase(getContext())
                .contenuFicheDao().findContenuFicheFromId(idFiche);

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv = (ExpandableTextView) getView().findViewById(R.id
                .expand_text_viewRappelAnatomique);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv.setText(listeFiches.getRappelAnatomique());

        // sample code snippet to set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView) getView().findViewById(R.id
                .expand_text_view);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(listeFiches.getMaladie());


        ExpandableTextView expTvPourquoiOpérer = (ExpandableTextView) getView().findViewById(R.id
                .expand_text_view_PourquoiOpération);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvPourquoiOpérer.setText(listeFiches.getRisquesMaladie());


        ExpandableTextView expTvPrincipeIntervention = (ExpandableTextView) getView().findViewById
                (R.id.expand_text_view_PrincipesIntervention);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvPrincipeIntervention.setText(listeFiches.getPrincipe());

        ExpandableTextView expTvTechniquesOperatoires = (ExpandableTextView) getView().findViewById
                (R.id.expand_text_view_TechniquesOperatoires);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesOperatoires.setText(listeFiches.getTechnique());


        ExpandableTextView expTvTechniquesSuitesHabituelles = (ExpandableTextView) getView()
                .findViewById(R.id.expand_text_view_SuitesHabituelles);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesSuitesHabituelles.setText(listeFiches.getSuites());


        ExpandableTextView expTvTechniquesRisquesLiés = (ExpandableTextView) getView()
                .findViewById(R.id.expand_text_view_Risquesliés);
        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTvTechniquesRisquesLiés.setText(listeFiches.getRisquesOperation());


        ExpandableTextView expTvTechniquesSuivi = (ExpandableTextView) getView()
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
                    allIdFicheStocked2.remove(new Integer(ficheInformative.getIdFiche()));
                    tinydb2.putListInt(LISTE_FICHE_INFORMATIVE_ID_FICHE, allIdFicheStocked2);
                    Toasty.warning(getContext(), "Fiche supprimée des favoris !", Toast
                            .LENGTH_SHORT, true).show();
                } else {
                    allIdFicheStocked2.add(ficheInformative.getIdFiche());
                    Set set2 = new HashSet();
                    set2.addAll(allIdFicheStocked2);
                    ArrayList<Integer> uniqueIdStocked2 = new ArrayList<Integer>(set2);
                    tinydb2.putListInt(LISTE_FICHE_INFORMATIVE_ID_FICHE, uniqueIdStocked2);

                    //Toast.makeText(getContext(), "Fiche ajoutée aux favoris", Toast.LENGTH_LONG)
                    //        .show();
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
