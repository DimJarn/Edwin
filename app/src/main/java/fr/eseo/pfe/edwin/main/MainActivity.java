package fr.eseo.pfe.edwin.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import fr.eseo.pfe.edwin.AProposActivity;
import fr.eseo.pfe.edwin.AccueilActivity;
import fr.eseo.pfe.edwin.AideActivity;
import fr.eseo.pfe.edwin.FicheActivity;
import fr.eseo.pfe.edwin.GlossaireActivity;
import fr.eseo.pfe.edwin.ParametresActivity;
import fr.eseo.pfe.edwin.QuizzActivity;
import fr.eseo.pfe.edwin.R;

import static fr.eseo.pfe.edwin.utilitaires.LogUtil.logD;
import static fr.eseo.pfe.edwin.utilitaires.LogUtil.makeLogTag;

/**
 * Classe Main qui gère :
 * - la première utilisation
 * - le menu
 */
public abstract class MainActivity extends AppCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener {
    public static final String NAME_FILE_CGU = "file_cgu.txt";//Name of the final to read for
    // terms and conditions use
    public static final String CONDITIONS_GENERALES_D_UTILISATION = "Conditions générales " +
            "d'utilisation :";
    public static final String YOUR_OWN_APPLICATION_PACKAGENAME_COM = "YOUR.OWN.APPLICATION" +
            ".PACKAGENAME.COM";
    public static final String NULL_TUTORIAL = "NullTutorial";
    protected static final int NAV_DRAWER_ITEM_INVALID = -1;
    private static final String TAG = makeLogTag(MainActivity.class);
    private DrawerLayout drawerLayout;
    private Toolbar actionBarToolbar;
    private NavigationView navigationView;
    private SharedPreferences Prefs;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /**
         * The main menu
         */
        setupNavDrawer();


        //configureNavigationView();
        /*FicheInformative ficheInformative = new FicheInformative(1,"Pneumo",null,1);
        ContenuFiche contenuFiche = new ContenuFiche(1,null,null,null,null,null,null,null);

        contenuFiche.setIdContenuFiche(1);
        contenuFiche.setMaladie("Vous avez présenté un décollement de plèvre appelé pneumothorax,
         lié à la présence anormale d’air dans l’espace pleural.\n" +
                "Ce pneumothorax est le plus fréquemment dû à la rupture de petites bulles
                présentes à la surface du poumon. Parfois une maladie pulmonaire est retrouvée,
                comme la BronchoPneumopathie Chronique Obstructive ou BPCO. Enfin, chez la femme
                jeune, il peut y avoir un lien avec les périodes de menstruation.\n");
        contenuFiche.setRisquesMaladie("Le risque principal du pneumothorax est la suffocation et
         l’arrêt cardiorespiratoire. Le passage massif d’air dans l’espace pleural peut empêcher
         la ventilation normale du poumon décollé, mais aussi celle de l’autre poumon.\n" +
                "Lors du premier épisode de pneumothorax chez un sujet sain par ailleurs, un
                simple drainage peut être proposé. Cependant, en cas de récidive, il est admis
                d’opérer afin d’éviter une nouvelle récidive.\n");
        contenuFiche.setPrincipe("Les buts de l’intervention sont d’éviter toute récidive, en
        optenant une réexpansion complète et un accolement du poumon à la paroi. La chirurgie
        pourra également permettre d’obtenir un diagnostic plus précis et de traiter la cause du
        pneumothorax.\n" +
                "-\tExploration de la cavité thoracique\n" +
                "-\tTraitement de la cause = traitement des lésions bulleuses\n" +
                "-\tSymphyse pleurale = accolement du poumon à la paroi\n" +
                "Pour réaliser cet accolement, plusieurs techniques sont envisageables et
                utilisables en même temps :\n" +
                "-\tirritation de la plèvre pariétale soit chimiquement par un produit irritant
                (talc par exemple), soit mécaniquement en frottant fort la plèvre\n" +
                "-\t« arrachage » de la plèvre pariétale, sur plusieurs cm = pleurectomie.\n" +
                "Le principe de ces 2 techniques est de déclencher une réaction inflammatoire qui
                 permettra l’accolement du poumon à la paroi.\n");
        contenuFiche.setTechnique("De nos jours, l’utilisation d’une caméra pour visualiser
        l’intérieur du thorax – vidéothoracoscopie - est répandue et permet de vous opérer en
        évitant la thoracotomie c’est-à-dire l’ouverture plus large et l’écartement des côtes,
        source de douleurs post-opératoires .\n" +
                "Cependant, seul le résultat final compte et la technique utilisée sera celle qui
                 permettra le meilleur résultat.\n");
        contenuFiche.setSuites("Vous sortirez de la salle d’opération avec un ou deux drains
        thoraciques. Ce sont des tuyaux qui permettront d’aspirer l’air ou les liquides produit
        par le thorax pendant ou après l’intervention. Ils seront retirés le plus souvent dans
        les 4 jours suivants l’intervention, mais il est parfois nécessaire de les laisser plus
        longtemps.\n" +
                "Certains poumons étant plus fragiles que d’autres, il est parfois nécessaire
                d’intervenir une seconde fois pour stopper les éventuelles fuites d’air
                persistantes.\n");
        contenuFiche.setRisquesOperation("Comme toute intervention chirurgicale, il existe des
        risques de douleurs post-opératoires, de saignement et d’infections. L’hospitalisation
        peut être prolongée de quelques jours voire une réintervention peut être nécessaire.\n" +
                "Plus spécifiquement pour cette chirurgie thoracique, un bullage du drain pendant
                 plusieurs jours, ou un accolement incomplet du poumon peuvent apparaître. \n" +
                "Plus rarement, un écoulement de lymphe dans le drain ou des troubles au niveau
                de l’œil du côté opéré peuvent être retrouvés.\n");
        contenuFiche.setSuivi("Un arrêt de travail et d’activités physique d’un mois est
        classique.\n" +
                "Les sports tels que la plongée sous-marine, ainsi que les instruments de musique
                 à vent (trompette, saxophone…) seront contre-indiqués à vie.\n" +
                "Il faudra attendre la consultation de contrôle avec le chirurgien pour savoir si
                 vous pourrez reprendre l’avion.\n" +
                "Une consultation avec votre chirurgien avec radiographie des poumons à un mois
                est prévue.\n" +
                "Vous serez également revu par votre pneumologue afin de faire le point sur vos
                capacités pulmonaires.\n");

        ArrayList<FicheInformative> ficheInformativeArrayList = new ArrayList<FicheInformative>();

        ficheInformativeArrayList.add(ficheInformative);

        ArrayList<ContenuFiche> contenuFicheArrayList = new ArrayList<ContenuFiche>();

        contenuFicheArrayList.add(contenuFiche);

        DatabaseInitializer.populateAsync(EdwinDatabase.getAppDatabase(this),
        ficheInformativeArrayList, contenuFicheArrayList,
                new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList
                ());*/

    }

    /**
     * Sets up the navigation drawer.
     */
    private void setupNavDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout == null) {
            // current activity does not have a drawer.
            return;
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerSelectListener(navigationView);
            setSelectedItem(navigationView);
        }

        logD(TAG, "navigation drawer setup finished");
    }

    private void configureNavigationView() {
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
     * Creates the item click listener.
     *
     * @param navigationView the navigation view
     */
    private void setupDrawerSelectListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        onNavigationItemClicked(menuItem.getItemId());
                        return true;
                    }
                });
        navigationView.setItemIconTintList(null); // Very important --> to show the icon and not
        // only the main line
    }

    /**
     * Handles the navigation item click.
     *
     * @param itemId the clicked item
     */
    private void onNavigationItemClicked(final int itemId) {
        if (itemId == getSelfNavDrawerItem()) {
            // Already selected
            closeDrawer();
            return;
        }
        goToNavDrawerItem(itemId);
    }

    /**
     * Handles the navigation item click and starts the corresponding activity.
     *
     * @param item the selected navigation item
     */
    private void goToNavDrawerItem(int item) {
        switch (item) {
            case R.id.accueil:
                startActivity(new Intent(this, AccueilActivity.class));
                finish();
                break;
            case R.id.fiches:
                startActivity(new Intent(this, FicheActivity.class));
                break;
            case R.id.glossaire:
                startActivity(new Intent(this, GlossaireActivity.class));
                break;
            case R.id.quiz:
                startActivity(new Intent(this, QuizzActivity.class));
                break;
            case R.id.aide:
                startActivity(new Intent(this, AideActivity.class));
                break;
            case R.id.apropos:
                startActivity(new Intent(this, AProposActivity.class));
                break;
            case R.id.parametres:
                startActivity(new Intent(this, ParametresActivity.class));
                break;
            case R.id.nav_quit:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                this.startActivity(intent);
                break;
        }
    }

    /**
     * Provides the action bar instance.
     *
     * @return the action bar.
     */
    protected ActionBar getActionBarToolbar() {
        if (actionBarToolbar == null) {
            actionBarToolbar = (Toolbar) findViewById(R.id.toolbar_real);

            if (actionBarToolbar != null) {
                setSupportActionBar(actionBarToolbar);

            }
        }
        return getSupportActionBar();
    }


    /**
     * Returns the navigation drawer item that corresponds to this Activity. Subclasses
     * have to override this method.
     */
    protected int getSelfNavDrawerItem() {
        return NAV_DRAWER_ITEM_INVALID;
    }

    protected void openDrawer() {
        if (drawerLayout == null)
            return;

        drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer() {
        if (drawerLayout == null)
            return;

        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public abstract boolean providesActivityToolbar();

    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
