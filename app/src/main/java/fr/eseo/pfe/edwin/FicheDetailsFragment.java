package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eseo.pfe.edwin.Util.ExpandableAdapter;
import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.EdwinDatabase;

public class FicheDetailsFragment extends Fragment {
    private ExpandableListView listView;
    private ExpandableAdapter listAdapter;
    private List<String> listDataheader;
    private HashMap<String, List<String>> listHash;


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
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AFFICHE LA VUE DU DETAIL DE GLOSSAIRE
        super.onCreate(savedInstanceState);
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
        initData();
        listAdapter = new ExpandableAdapter(getContext(),listDataheader,listHash);
        listView.setAdapter(listAdapter);
    }


    private void initData(){

        ContenuFiche listeFiches = EdwinDatabase.getAppDatabase(listView.getContext()).contenuFicheDao().findContenuFicheFromId(1);

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

        List<String> rappel =  new ArrayList<>();
        //rappel.add();

        List<String>  maladie =  new ArrayList<>();
        maladie.add(listeFiches.getMaladie());

        List<String>  pourquoi =  new ArrayList<>();
        pourquoi.add(listeFiches.getRisquesMaladie());

        List<String>  principes =  new ArrayList<>();
        principes.add(listeFiches.getPrincipe());

        List<String>  techniques =  new ArrayList<>();
        techniques.add(listeFiches.getTechnique());

        List<String>  suites =  new ArrayList<>();
        suites.add(listeFiches.getSuites());

        List<String>  risques =  new ArrayList<>();
        risques.add(listeFiches.getRisquesOperation());

        List<String>  suivi =  new ArrayList<>();
        suivi.add(listeFiches.getSuivi());

        listHash.put(listDataheader.get(0),rappel);
        listHash.put(listDataheader.get(1),maladie);
        listHash.put(listDataheader.get(2),pourquoi);
        listHash.put(listDataheader.get(3),principes);
        listHash.put(listDataheader.get(4),techniques);
        listHash.put(listDataheader.get(5),suites);
        listHash.put(listDataheader.get(6),risques);
        listHash.put(listDataheader.get(7),suivi);
    }
}
