package fr.eseo.pfe.edwin;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;

public class FichesActivity extends Fragment {

    FragmentActivity listener;
    private ListView mListView;

    public static FichesActivity newInstance() {
        return (new FichesActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fiches, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.listView);

        List<FicheInformative> listeFiches = EdwinDatabase.getAppDatabase(view.getContext()).ficheInformativeDao().findAllFichesInformatives();

        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        //Création d'une HashMap pour insérer les informations du premier item de notre listView
        map = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        map.put("titre", "Word");
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.drawable.logo_dico));
        map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);




        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView
        for(FicheInformative ficheInformative : listeFiches){
            map = new HashMap<String, String>();
            map.put("titre", ficheInformative.getNomOperation());
            map.put("img", String.valueOf(R.drawable.logo_cancel));
            map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
            listItem.add(map);
        }




        map = new HashMap<String, String>();
        map.put("titre", "Excel");
        map.put("img", String.valueOf(R.drawable.logo_cancel));
        map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Power Point");
        map.put("img", String.valueOf(R.drawable.logo_heart));
        map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "Outlook");
        map.put("img", String.valueOf(R.drawable.logo_info));
        map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
        listItem.add(map);

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (getContext(), listItem, R.layout.fiche_item,
                new String[] {"img", "titre","arrow"}, new int[] {R.id.img, R.id.titre, R.id.fleche});

        //On attribut à notre listView l'adapter que l'on vient de créer
        mListView.setAdapter(mSchedule);

        //Enfin on met un écouteur d'évènement sur notre listView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //on récupère la HashMap contenant les infos de notre item (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) mListView.getItemAtPosition(position);
                //on redirige vers la bonne fiche
                Intent intent = new Intent(getContext(), FicheDetailsActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}