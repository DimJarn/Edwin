package fr.eseo.pfe.edwin;

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
 * Activté A Propos, extends de la Main Activité
 * Affichage de la page A propos et intégration du menu
 * Pas de boutons sur cette activité
 * Pas de fragments liés a cette activité
 *
 * @author dimitrijarneau
 */
public class AProposActivity extends MainActivity {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_propos_activity);
        setupToolbar(); // to integrate the menu
        animateIconMenu();
    }

    /**
     * Methode privée pour animer l'icone
     */
    private void animateIconMenu() {
        //to animate icon menu
        toolbar = findViewById(R.id.toolbar_real);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
    }

    /**
     * Methode privée pour initialiser le drawer toogle
     *
     * @return the action bar
     */
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
     * Method to import the toolbar and menu
     */
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Informations");

    }

    /**
     * Overrided Methods
     **/

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    /**
     * Method to change the item selected in the menu
     *
     * @return id of selected item
     */
    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.apropos;
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
            Log.d("AProposActivity", "Erreur");
        }
        return super.onOptionsItemSelected(item);
    }

}
