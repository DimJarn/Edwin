package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.eseo.pfe.edwin.Util.ExpandableAdapter;
import fr.eseo.pfe.edwin.Util.TinyDB;
import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;

public class FicheDetailsFragment extends Fragment {
    private ExpandableListView listView;
    private ExpandableAdapter listAdapter;
    private List<String> listDataheader;
    private HashMap<String, List<String>> listHash;
    private TextView textViewTitre;
    private FicheInformative ficheInformative;

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
        listView = (ExpandableListView) getView().findViewById(R.id.expand);

        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("idFiche");

        initData(myInt);
        listAdapter = new ExpandableAdapter(getContext(), listDataheader, listHash);
        listView.setAdapter(listAdapter);

    }


    private void initData(int idFiche) {

        ContenuFiche listeFiches = EdwinDatabase.getAppDatabase(listView.getContext())
                .contenuFicheDao().findContenuFicheFromId(idFiche);
        ficheInformative = EdwinDatabase.getAppDatabase(listView.getContext())
                .ficheInformativeDao().findFicheInformativeFromId(idFiche);

        textViewTitre = (TextView) getView().findViewById(R.id.nom_operation);
        textViewTitre.setText(ficheInformative.getNomOperation());

        listDataheader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataheader.add("Rappel anatomique");
        listDataheader.add("La maladie");
        listDataheader.add("Pourquoi vous faire opérer - alternatives ?");
        listDataheader.add("Principes de l'intervention");
        listDataheader.add("Techniques opératoires");
        listDataheader.add("Suites habituelles");
        listDataheader.add("Risques liés");
        listDataheader.add("Suivi post-opératoire");

        List<String> rappel = new ArrayList<>();
        //rappel.add(titre.getNomOperation());

        List<String> maladie = new ArrayList<>();
        maladie.add(listeFiches.getMaladie());

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

        listHash.put(listDataheader.get(0), rappel);
        listHash.put(listDataheader.get(1), maladie);
        listHash.put(listDataheader.get(2), pourquoi);
        listHash.put(listDataheader.get(3), principes);
        listHash.put(listDataheader.get(4), techniques);
        listHash.put(listDataheader.get(5), suites);
        listHash.put(listDataheader.get(6), risques);
        listHash.put(listDataheader.get(7), suivi);
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
        List<Integer> listeIdsFichesFavorites = tinydb.getListInt("listeFicheInformativeIdFiche");
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
            case R.id.action_settings:
                // your logic
                Toast.makeText(getContext(), "Item 1 Selected" + ficheInformative.getIdFiche(),
                        Toast
                                .LENGTH_LONG).show();

                TinyDB tinydb = new TinyDB(getContext());
                // tinydb.remove("listeFicheInformativeIdFiche");
                ArrayList<Integer> allIdFicheStocked = tinydb.getListInt
                        ("listeFicheInformativeIdFiche");
                if (allIdFicheStocked.contains(ficheInformative.getIdFiche())) {
                    allIdFicheStocked.remove(new Integer(ficheInformative.getIdFiche()));
                    tinydb.putListInt("listeFicheInformativeIdFiche", allIdFicheStocked);
                    Toast.makeText(getContext(), "La fiche " + ficheInformative.getNomOperation()
                            + " a été supprimée des favs", Toast
                            .LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "La fiche ne fait pas partie des favs", Toast
                            .LENGTH_LONG).show();
                }

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();

                return true;
            case R.id.item1:
                TinyDB tinydb2 = new TinyDB(getContext());
                ArrayList<Integer> allIdFicheStocked2 = tinydb2.getListInt
                        ("listeFicheInformativeIdFiche");
                if (allIdFicheStocked2.contains(ficheInformative.getIdFiche())) {
                    Toast.makeText(getContext(), "La fiche " + ficheInformative.getNomOperation()
                            + " est déjà dans les favoris !", Toast
                            .LENGTH_LONG).show();
                } else {

                    allIdFicheStocked2.add(ficheInformative.getIdFiche());
                    Set set2 = new HashSet();
                    set2.addAll(allIdFicheStocked2);
                    ArrayList<Integer> uniqueIdStocked2 = new ArrayList<Integer>(set2);
                    tinydb2.putListInt("listeFicheInformativeIdFiche", uniqueIdStocked2);

                    Toast.makeText(getContext(), "Fiche ajoutée aux favoris", Toast.LENGTH_LONG)
                            .show();

                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                    ft2.detach(this).attach(this).commit();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
