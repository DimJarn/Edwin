package fr.eseo.pfe.edwin;


import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.Glossaire;

/**
 * @author dimitrijarneau
 * Fragment Glossaire
 * Affichage de la liste des mots et filtre de recherche
 */
public class GlossaireFragment extends ListFragment implements SearchView.OnQueryTextListener,
        MenuItem.OnActionExpandListener {
    List<String> mAllValues;
    private ArrayAdapter<String> mAdapter;
    private Context mContext;
    private List<Glossaire> listeMotsGlossaire;

    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new GlossaireFragment());
    }

    /**
     * @param savedInstanceState l'instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    /**
     * Methode permettant une action après un click sur un élement
     *
     * @param listView la listview
     * @param v        la vue
     * @param position la position
     * @param id       l'id
     */
    public void onListItemClick(ListView listView, View v, int position, long id) {

        String item = (String) listView.getAdapter().getItem(position);
        if (getActivity() instanceof OnItem1SelectedListener) {
            ((OnItem1SelectedListener) getActivity()).onItem1SelectedListener(item);
        }
        getFragmentManager().popBackStack();

        //On récupére le mot selectionné en recherchant si le string récupéré est bien dans la db
        Glossaire termeMotSelect = EdwinDatabase.getAppDatabase(v.getContext()).glossaireDao()
                .findItemGlossaireFromName(item);
        // On récupére son id dans la liste initiale et on transmet
        int idTermeMotSelect = termeMotSelect.getIdTerme();

        Bundle bundle = new Bundle();
        bundle.putInt("idTerme", idTermeMotSelect);

        Fragment fragment = GlossaireDetailsFragment.newInstance();
        fragment.setArguments(bundle);
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id
                .layout_fragment1, fragment).commit();
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
        View view = inflater.inflate(R.layout.glossaire_fragment, container, false);
        ListView listView = view.findViewById(android.R.id.list);
        TextView emptyTextView = view.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyTextView);

        populateList(view);

        return view;
    }

    /**
     * Methode de creation barre de recherche
     *
     * @param menu     le menu
     * @param inflater l'inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Rechercher");

        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Boolean
     *
     * @param query la recherche
     * @return boolean
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    /**
     * Methode de recherche
     *
     * @param newText le nouveau texte
     * @return le boolean (false)
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<String> filteredValues = new ArrayList<>(mAllValues);
        for (String value : mAllValues) {
            if (!value.toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
                mAdapter.notifyDataSetChanged();
            }
        }

        mAdapter = new ArrayAdapter<>(mContext, R.layout.textview_glossaire, filteredValues);
        setListAdapter(mAdapter);

        return false;
    }

    /**
     * Methode reset recherche
     */
    public void resetSearch() {
        mAdapter = new ArrayAdapter<>(mContext, R.layout.textview_glossaire, mAllValues);
        setListAdapter(mAdapter);
    }

    /**
     * boolean
     *
     * @param item l'item selectionné
     * @return un boolean
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    /**
     * boolean
     *
     * @param item l'item selectionné
     * @return un boolean
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    /**
     * Affectation de la liste de mot
     */
    private void populateList(View view) {
        //on recupere la liste de mots du glossaire
        listeMotsGlossaire = EdwinDatabase.getAppDatabase(view.getContext()).glossaireDao()
                .findAllGlossaires();

        mAllValues = new ArrayList<>();
        for (Glossaire motGlossaire : listeMotsGlossaire) {
            mAllValues.add(motGlossaire.getNomTerme());
        }
        //On trie par ordre alpha
        Collections.sort(mAllValues, String.CASE_INSENSITIVE_ORDER);

        mAdapter = new ArrayAdapter<>(mContext, R.layout.textview_glossaire, mAllValues);
        setListAdapter(mAdapter);
    }

    /**
     * Selection de l'item
     */
    public interface OnItem1SelectedListener {
        void onItem1SelectedListener(String item);
    }

}