package fr.eseo.pfe.edwin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eseo.pfe.edwin.Util.ExpandableAdapter;
import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;

public class FicheDetailsActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableAdapter listAdapter;
    private List<String> listDataheader;
    private HashMap<String, List<String>> listHash;

    protected void onCreate(Bundle savedInstanceState) {

        //AFFICHE LA VUE DU DETAIL DE FICHE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fiche_details_fragment);

        final FicheInformative ficheInformative = (FicheInformative) getIntent().getSerializableExtra("fiche");

        listView = (ExpandableListView)findViewById(R.id.expand);
        initData(ficheInformative.getIdFiche());
        listAdapter = new ExpandableAdapter(this,listDataheader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData(int idFiche){

        ContenuFiche listeFiches = EdwinDatabase.getAppDatabase(listView.getContext()).contenuFicheDao().findContenuFicheFromId(idFiche);

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

        List<String>  rappel =  new ArrayList<>();
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
