package fr.eseo.pfe.edwin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        setContentView(R.layout.fiche_contenu);

        listView = (ExpandableListView)findViewById(R.id.expand);
        initData();
        listAdapter = new ExpandableAdapter(this,listDataheader,listHash);
        listView.setAdapter(listAdapter);

    }

    private void initData(){

        ContenuFiche listeFiches = EdwinDatabase.getAppDatabase(listView.getContext()).ficheInformativeDao().findFicheInformativeFromIdAndContent(1);


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
        //rappel.add(ficheInformative.getNomOperation());
        //System.out.println(listeFiches.getMaladie());

        List<String>  maladie =  new ArrayList<>();
        //rappel.add(ficheInformative.getNomOperation());

        List<String>  pourquoi =  new ArrayList<>();
        pourquoi.add("Prends des medocs mon frere ça ira tu verras");

        List<String>  principes =  new ArrayList<>();
        principes.add("bah tu ne me laisses pas le choix je crois bien");

        List<String>  techniques =  new ArrayList<>();
        techniques.add("Bah ecoute bismillah la famillle et toi ?");

        List<String>  suites =  new ArrayList<>();
        suites.add("J'ai mal a mon gros orteil vois tu ");

        List<String>  risques =  new ArrayList<>();
        risques.add("Prends des medocs mon frere ça ira tu verras");

        List<String>  suivi =  new ArrayList<>();
        suivi.add("bah tu ne me laisses pas le choix je crois bien");

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
