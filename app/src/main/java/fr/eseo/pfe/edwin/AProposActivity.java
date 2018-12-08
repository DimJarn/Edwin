package fr.eseo.pfe.edwin;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import fr.eseo.pfe.edwin.Main.MainActivity;

/**
 * Acitivté A Propos, extends de la Main Activité
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

    private void animateIconMenu() {
        //to animate icon menu
        toolbar = (Toolbar) findViewById(R.id.toolbar_real);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
     * Method to import the toolbar and menu
     */
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Informations");

    }

    /**
     * Updated the checked item in the navigation drawer
     *
     * @param navigationView the navigation view
     */
    private void setSelectedItem(NavigationView navigationView) {
        // Which navigation item should be selected?
        int selectedItem = getSelfNavDrawerItem(); // subclass has to override this method
        navigationView.setCheckedItem(selectedItem);
    }

    /**
     * Overrided Methods
     **/

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
     * @Override public boolean onCreateOptionsMenu(Menu menu) {
     * getMenuInflater().inflate(R.menu.sample_actions, menu);
     * return true;
     * }
     **/
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
