package fr.eseo.pfe.edwin;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import fr.eseo.pfe.edwin.Main.MainActivity;

public class AideActivity extends MainActivity {
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aide_activity);
        setupToolbar(); // to integrate the menu

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
        getSupportActionBar().setTitle("Aide");

    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
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
        return R.id.aide;
    }

    /**
     * @Override public boolean onCreateOptionsMenu(Menu menu) {
     * getMenuInflater().inflate(R.menu.sample_actions, menu);
     * return true;
     * }
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            openDrawer();
        } else {
            Log.d("AideActivity: ", "Default value");
        }
        return super.onOptionsItemSelected(item);
    }
}
