package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

import fr.eseo.pfe.edwin.main.MainActivity;


/**
 * Activté VosFicheActivity, extends de la Main Activité
 * Affichage de la page VosFicheActivity et intégration du menu
 * Présence boutons sur cette activité
 * Fragments liés a cette activité
 */
public class VosFichesActivity extends MainActivity {
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vos_fiches_activity); // on set un layout avec rien pour ensuite
        // afficher le
        // fragment
        setupToolbar(); // on integre le menu
        animateIconMenu();
        Fragment fragment = VosFichesFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.fiche_vide, fragment).commit();
    }

    private void animateIconMenu() {
        //to animate icon menu
        toolbar = findViewById(R.id.toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not
        // require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string
                .drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    /**
     * Methode qui va creer et importer le toolbar pour naviguer vers le menu
     */
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Vos fiches");

    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.fiches;
    }

    /**
     * Methode qui attribue une action en fonction de l'option selectionnée
     *
     * @param item l'item du menu selectionné
     * @return item l'item seletionné
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            openDrawer();
        } else {
            Log.d("VosFichesActivity", "Erreur");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
