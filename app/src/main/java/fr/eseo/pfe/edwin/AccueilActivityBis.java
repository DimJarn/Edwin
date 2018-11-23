package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import fr.eseo.pfe.edwin.Main.MainActivity;

/**
 * @author dimitrijarneau
 * Accueil activity
 * CLasse qui ouvre la premiere page d'accueil et qui gere les boutons
 * Extends MainActivity qui integre le menu
 * Possibilité d'ajouter des fragments a partir de cette activité
 */
public class AccueilActivityBis extends MainActivity implements View.OnClickListener {
    private Fragment fragmentAide;
    private Fragment fragmentAPropos;
    private Fragment fragmentFiches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);
        setupToolbar();
        setUpButton();

    }

    /**
     * Methode qui va creer et importer le toolbar pour naviguer vers le menu
     */
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Accueil");

    }

    /**
     * Methode pour initialiser les boutons de la page d'accueil
     */
    private void setUpButton() {
        Button buttonHelp = (Button) findViewById(R.id.buttonNeedHelp);
        buttonHelp.setOnClickListener(this);

        Button buttonYourOperation = (Button) findViewById(R.id.buttonYourOperation);
        buttonYourOperation.setOnClickListener(this);

        Button buttonMoreInfo = (Button) findViewById(R.id.buttonMoreInfo);
        buttonMoreInfo.setOnClickListener(this);
    }

    /**
     * Methode pour attribuer les actions aux boutons
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYourOperation:
                System.out.println("Test");
                //this.showFichesFragment();
                break;
            case R.id.buttonNeedHelp:
                startActivity(new Intent(this, AideActivityBis.class));
                break;
            case R.id.buttonMoreInfo:
                startActivity(new Intent(this, AProposActivityBis.class));
                break;
        }
    }

    /**
     * @Override public boolean onCreateOptionsMenu(Menu menu) {
     * getMenuInflater().inflate(R.menu.sample_actions, menu);
     * return true;
     * }
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.accueil;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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

    // Methode générique permettant de gérer l'affichage des fragment dans l'element du layout activity_main_frame_layout
    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }
/**
 private void showAProposFragment(){
 if (this.fragmentAPropos == null) this.fragmentAPropos = AProposActivityBis.newInstance();
 this.startTransactionFragment(this.fragmentAPropos);
 }

 **/
}
