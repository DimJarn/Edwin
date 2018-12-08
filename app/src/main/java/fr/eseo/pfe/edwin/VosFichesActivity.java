package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import fr.eseo.pfe.edwin.Main.MainActivity;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
     * Methode qui va creer et importer le toolbar pour naviguer vers le menu
     */
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vos fiches");

    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.fiches;
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
            Log.d("VosFichesActivity", "Erreur");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
