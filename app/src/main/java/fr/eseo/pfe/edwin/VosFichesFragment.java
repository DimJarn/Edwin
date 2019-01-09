package fr.eseo.pfe.edwin;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import fr.eseo.pfe.edwin.Util.TinyDB;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;

public class VosFichesFragment extends Fragment {

    FragmentActivity listener;
    private ListView mListView;

    public static VosFichesFragment newInstance() {
        return (new VosFichesFragment());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.vos_fiches_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.listView);
        TextView emptyTextView = view.findViewById(android.R.id.empty);
        mListView.setEmptyView(emptyTextView);
        // setUpButton();

        TinyDB tinydb = new TinyDB(getContext());
        List<Integer> listeIdsFichesFavorites = tinydb.getListInt("listeFicheInformativeIdFiche");
        final List<FicheInformative> fichesFavs = new ArrayList<>();

        if (!listeIdsFichesFavorites.isEmpty()) {
            for (int idFiche = 0; idFiche < listeIdsFichesFavorites.size(); idFiche++) {
                FicheInformative ficheInfoFav = EdwinDatabase.getAppDatabase(view.getContext())
                        .ficheInformativeDao().findFicheInformativeFromId(listeIdsFichesFavorites
                                .get(idFiche));
                fichesFavs.add(ficheInfoFav);
            }
        } else {
            Toasty.info(getContext(), "Aucune fiche dans les favoris.", Toast.LENGTH_SHORT, true)
                    .show();
        }

        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;


        //On refait la manip plusieurs fois avec des données différentes pour former les items de
        // notre ListView
        for (FicheInformative ficheInformative : fichesFavs) {
            map = new HashMap<String, String>();
            map.put("titre", ficheInformative.getNomOperation());
            map.put("img", String.valueOf(R.drawable.logo_star));
            map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
            listItem.add(map);
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list
        // (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mSchedule = new SimpleAdapter(getContext(), listItem, R.layout.fiche_item,
                    new String[]{"img", "titre", "arrow"}, new int[]{R.id.img, R.id.titre, R.id
                    .fleche});
        }

        //On attribut à notre listView l'adapter que l'on vient de créer
        mListView.setAdapter(mSchedule);

        //Enfin on met un écouteur d'évènement sur notre listView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                int idFiche = fichesFavs.get(position).getIdFiche();
                Bundle bundle = new Bundle();
                bundle.putInt("idFiche", idFiche);
                Fragment fragment = FicheDetailsFragment.newInstance();
                fragment.setArguments(bundle);
                // Très important !!
                //la méthode .addToBackStack permet d'utiliser le bouton retour dans le fragment
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id
                        .layout_fragment_vos_fiche_1, fragment).commit();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    /* *//**
     * Methode pour initialiser les boutons de la page d'accueil
     *//*
    private void setUpButton() {
        final FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getContext(), "Ajouter des fiches !", Toast.LENGTH_SHORT,
                        true)
                        .show();
                startActivity(new Intent(getContext(), FicheActivity.class));

            }
        });

    }*/

}
