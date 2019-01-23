package fr.eseo.pfe.edwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Cette classe n'est plus utilisée maintenant
 */
public class MenuActivityy extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activityy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 6 - Configure all views

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        //appel de methode pour éviter d'avoir un fragment vide au lancement de l'app
        // this.showFirstFragment();
    }

    private Fragment fragmentAide;
    private Fragment fragmentAPropos;
    private Fragment fragmentFiches;
    private Fragment fragmentGlossaire;
    private Fragment fragmentQuiz;
    private Fragment fragmentAccueil;

    private static final int FRAGMENT_ACCUEIL = 0;
    private static final int FRAGMENT_AIDE = 1;
    private static final int FRAGMENT_APROPOS = 2;
    private static final int FRAGMENT_FICHES = 3;
    private static final int FRAGMENT_GLOSSAIRE = 4;
    private static final int FRAGMENT_QUIZ = 5;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.accueil :
                //this.showFragment(FRAGMENT_ACCUEIL);
                break;
            case R.id.aide :
                // this.showFragment(FRAGMENT_AIDE);
                break;
            case R.id.apropos:
                //this.showFragment(FRAGMENT_APROPOS);
                break;
            case R.id.fiches:
                //this.showFragment(FRAGMENT_FICHES);
                break;
            case R.id.glossaire:
                //this.showFragment(FRAGMENT_GLOSSAIRE);
                break;
            case R.id.quiz:
                //this.showFragment(FRAGMENT_QUIZ);
                break;
            case R.id.nav_quit:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                this.startActivity(intent);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
/*
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_ACCUEIL :
                this.showAccueilFragment();
                break;
            case FRAGMENT_AIDE :
                this.showAideFragment();
                break;
            case FRAGMENT_APROPOS:
                this.showAProposFragment();
                break;
            case FRAGMENT_FICHES:
                this.showFichesFragment();
                break;
            case FRAGMENT_GLOSSAIRE:
                this.showGlossaireFragment();
                break;
            case FRAGMENT_QUIZ:
                this.showQuizFragment();
                break;
            default:
                break;
        }
    }


    private void showAccueilFragment(){
        if (this.fragmentAccueil == null) this.fragmentAccueil = AccueilActivity.newInstance();
        this.startTransactionFragment(this.fragmentAccueil);
    }

    private void showAideFragment(){
        if (this.fragmentAide == null) this.fragmentAide = AideActivity.newInstance();
        this.startTransactionFragment(this.fragmentAide);
    }

    private void showAProposFragment(){
        if (this.fragmentAPropos == null) this.fragmentAPropos = AProposActivity.newInstance();
        this.startTransactionFragment(this.fragmentAPropos);
    }

    private void showFichesFragment(){
        if (this.fragmentFiches == null) this.fragmentFiches = FichesActivity.newInstance();
        this.startTransactionFragment(this.fragmentFiches);
    }

    private void showGlossaireFragment(){
        if (this.fragmentGlossaire == null) this.fragmentGlossaire = GlossaireActivity.newInstance();
        this.startTransactionFragment(this.fragmentGlossaire);
    }

    private void showQuizFragment(){
        if (this.fragmentQuiz == null) this.fragmentQuiz = QuizActivity.newInstance();
        this.startTransactionFragment(this.fragmentQuiz);
    }
*/
// Generic method that will replace and show a fragment inside the MenuActivity Frame Layout
private void startTransactionFragment(Fragment fragment){
    if (!fragment.isVisible()){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main_frame_layout, fragment).commit();
    }
}
/*
    // Show first fragment when activity is created
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            this.showFragment(FRAGMENT_ACCUEIL);
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }
    */
}
