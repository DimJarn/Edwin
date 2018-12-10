package fr.eseo.pfe.edwin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.pfe.edwin.Main.MainActivity;
import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.DatabaseInitializer;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;
import fr.eseo.pfe.edwin.data.Glossaire;
import fr.eseo.pfe.edwin.data.JSON;
import fr.eseo.pfe.edwin.data.Question;
import fr.eseo.pfe.edwin.data.Quiz;

/**
 * @author dimitrijarneau
 * Accueil activity
 * CLasse qui ouvre la premiere page d'accueil et qui gere les boutons
 * Extends MainActivity qui integre le menu
 * Possibilité d'ajouter des fragments a partir de cette activité
 */
public class AccueilActivity extends MainActivity implements View.OnClickListener {

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);
        setupToolbar();
        setUpButton();

        toolbar = (Toolbar) findViewById(R.id.toolbar_real);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        //EdwinDatabase.getAppDatabase(this).clearAllTables();

        //populateDBFirstTime();
        //populateDBFicheEtContenu();
        //populateDBGlossaire();
        //populateDBQuiz();
        //populateDBQuiz2();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }



    private void populateDBFirstTime(){
        ArrayList<FicheInformative> ficheInformativeArrayList = JSON.getFiches();

        for (FicheInformative ficheInformative1 : ficheInformativeArrayList) {
            System.out.println("fiche : " + ficheInformative1.getNomOperation());
        }

        ArrayList<ContenuFiche> contenuFicheArrayList = JSON.getContenuFiches();

        for (ContenuFiche contenuFiche1 : contenuFicheArrayList) {
            System.out.println("contenu fiche : " + contenuFiche1.getIntro());
        }

        ArrayList<Glossaire> glossaire = JSON.getGlossaire();

        for (Glossaire terme : glossaire) {
            System.out.println("glossaire : " + terme.getNomTerme());
        }

        ArrayList<Quiz> quizArrayList = JSON.getQuiz();

        for (Quiz quiz : quizArrayList) {
            System.out.println("quiz : " + quiz.getNomQuiz());
        }

        ArrayList<Question> questions = JSON.getQuestions();

        for (Question question : questions) {
            System.out.println("question : " + question.getIntitule());
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            DatabaseInitializer.populateAsync(EdwinDatabase.getAppDatabase(this), ficheInformativeArrayList,
                    contenuFicheArrayList, glossaire, quizArrayList, questions);

            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }
    private void populateDBFicheEtContenu() {
        FicheInformative ficheInformative = new FicheInformative();

        ficheInformative.setNomOperation("gastro");
        ficheInformative.setRefContenuFiche(1);

        ContenuFiche contenuFiche = new ContenuFiche();

        contenuFiche.setIntro("Intro gastro");
        contenuFiche.setRappelAnatomique("Rappel gastro");
        contenuFiche.setMaladie("MALADIE MALADIE");
        contenuFiche.setIntro("test");
        contenuFiche.setRappelAnatomique("test");
        contenuFiche.setRisquesMaladie("RISQUES");
        contenuFiche.setPrincipe("Principe");
        contenuFiche.setTechnique("Technique");
        contenuFiche.setSuites("Suites");
        contenuFiche.setRisquesOperation("Risques op");
        contenuFiche.setSuivi("Suivi");

        FicheInformative ficheInformative2 = new FicheInformative();

        ficheInformative2.setNomOperation("pneumo");
        ficheInformative2.setRefContenuFiche(2);
        ficheInformative2.setIdFiche(2);

        ContenuFiche contenuFiche2 = new ContenuFiche();
        contenuFiche2.setIdContenuFiche(2);

        contenuFiche2.setIntro("Intro pneumo");
        contenuFiche2.setRappelAnatomique("Rappel pneumo");
        contenuFiche2.setMaladie("PNEUMO MALADIE");
        contenuFiche2.setRisquesMaladie("PNEUMO RISQUES");
        contenuFiche2.setPrincipe("PNEUMO Principe");
        contenuFiche2.setTechnique("PNEUMO Technique");
        contenuFiche2.setSuites("PNEUMO Suites");
        contenuFiche2.setRisquesOperation("PNEUMO Risques op");
        contenuFiche2.setSuivi("PNEUMO Suivi");


        //EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
        // (ficheInformative);
        //EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche(contenuFiche);
        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative2);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche(contenuFiche2);
    }

    private void populateDBQuiz() {
        Quiz quiz = new Quiz(1, "Test", 1);
        EdwinDatabase.getAppDatabase(this).quizDao().insertQuiz(quiz);

        Question question = new Question(1, "Tests question", "A", "B", "C", "C", 1);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question);

        Question question2 = new Question(2, "Tests question2", "A", "B", "C", "C", 1);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question2);

        Question question3 = new Question(3, "Tests question3", "A", "B", "C", "C", 1);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question3);

        Question question4 = new Question(4, "Tests question4", "A", "B", "C", "C", 1);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question4);

        Question question5 = new Question(5, "Tests question5", "A", "B", "C", "C", 1);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question5);

    }

    private void populateDBQuiz2() {
        Quiz quiz = new Quiz(2, "Quiz culture générale - Santé et médecine N°1\n" +
                "\n", 1);
        EdwinDatabase.getAppDatabase(this).quizDao().insertQuiz(quiz);

        Question question = new Question(1, "Un analeptique est censé :\n", "Provoquer le " +
                "sommeil", "Supprimer la douleur", "Tuer les microbes", "Supprimer la douleur", 2);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question);

        Question question2 = new Question(2, "Quel effet ont les barbituriques ?\n", "Ils donnent" +
                " de l'energie", "Ils provoquent le sommeil", "Ils soulagent la douleur", "Ils " +
                "provoquent le sommeil", 2);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question2);

        Question question3 = new Question(3, "Quel est le nom courant de la maladie influenza " +
                "?\n", "Angine", "Grippe", "Migraine", "Migraine", 2);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question3);

        Question question4 = new Question(4, "Le BCG est un vaccin contre :", "Le tétanos", "La " +
                "grippe", "La rage", "Le tétanos", 2);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question4);

        Question question5 = new Question(5, "L’infection d’une plaie est la manifestation de la " +
                "réaction locale de" +
                " l’organisme à une agression bactérienne. Les quatre phases suivantes " +
                "participent à c" +
                "ette réaction : A - Dilatation du capillaire. B - Phagocytose. C - Recrutement " +
                "des globules blancs. D - Multiplication des bactéries. Parmi les propositions " +
                "ci-dessous," +
                " laquelle correspond à l’ordre chronologique de ces phases ?", "D-A-C-B",
                "C-D-A-B",
                "B-C-D-A", "C-D-A-B", 2);
        EdwinDatabase.getAppDatabase(this).questionDao().insertQuestion(question5);

    }

    private void populateDBGlossaire() {
        Glossaire motGlossaire1 = new Glossaire();
        motGlossaire1.setIdTerme(1);
        motGlossaire1.setNomTerme("Symphyse pleurale");
        motGlossaire1.setDefinition("Egalement appelé pleurésie, l’épanchement pleural est défini" +
                " par " +
                "la présence de liquide entre les deux feuillets de la plèvre (le feuillet " +
                "viscéral " +
                "qui recouvre le poumon et le feuillet pariétal qui recouvre la face interne de " +
                "la " +
                "cage thoracique). Conséquences : l’épanchement peut provoquer une gêne et une " +
                "douleur" +
                " puis une difficulté respiratoire. Cette difficulté pour respirer, " +
                "appelée dyspnée, " +
                "est due à la compression du poumon par le liquide.");
        motGlossaire1.setRefFiche(2);

        Glossaire motGlossaire2 = new Glossaire();
        motGlossaire2.setIdTerme(2);
        motGlossaire2.setNomTerme("Plèvre viscérale");
        motGlossaire2.setDefinition("Membrane séreuse composée de deux feuillets, un " +
                "feuillet viscéral qui tapisse les poumons (plèvre pulmonaire ou viscérale) et un" +
                " " +
                "feuillet pariétal (plèvre pariétale) qui tapisse la surface interne de la paroi " +
                "thoracique, le diaphragme (plèvre diaphragmatique) et le médiastin (plèvre " +
                "médiastinale). " +
                "Ces deux feuillets se réfléchissent au niveau du hile, formant une cavité " +
                "virtuelle, " +
                "la cavité pleurale ; celle-ci contient normalement une petite quantité de " +
                "liquide séreux qui " +
                "facilite les mouvements de glissement, indispensables à la respiration.\n");
        motGlossaire2.setRefFiche(2);


        Glossaire motGlossaire3 = new Glossaire();
        motGlossaire3.setIdTerme(3);
        motGlossaire3.setNomTerme("Plèvre pariétale");
        motGlossaire3.setDefinition("En anatomie, la plèvre est une membrane lisse " +
                "et brillante située entre les poumons et la paroi thoracique." +
                " Elle est composée de deux feuillets. La plèvre pariétale est " +
                "le feuillet qui tapisse la paroi du thorax. Elle est séparée de l'autre " +
                "feuillet (la plèvre viscérale) par la cavité pleurale. " +
                "À l'intérieur de cette cavité se trouve un liquide appelé film liquidien. " +
                "La plèvre pariétale se divise en trois parties (costale, diaphragmatique et " +
                "médiastinale). Une atteinte de la plèvre peut être associée à une infection " +
                "(tuberculose) ou à une tumeur.");
        motGlossaire3.setRefFiche(2);

        Glossaire motGlossaire4 = new Glossaire();
        motGlossaire4.setIdTerme(4);
        motGlossaire4.setNomTerme("BronchoPneumopathie");
        motGlossaire4.setDefinition("La bronchopneumonie est une inflammation plus ou moins " +
                "étendue des bronches, des bronchioles, et des alvéoles pulmonaires, ainsi que du" +
                " tissu interstitiel pulmonaire, c'est-à-dire du parenchyme (tissu situé entre " +
                "ces organes). Les bronchioles sont les ramifications les plus fines des bronches" +
                " qui elles-mêmes correspondent à des conduits aériens, issus de la division de " +
                "la trachée en deux, et à chacune de leurs ramifications.");
        motGlossaire4.setRefFiche(2);

        Glossaire motGlossaire5 = new Glossaire();
        motGlossaire5.setIdTerme(5);
        motGlossaire5.setNomTerme("Drainage");
        motGlossaire5.setDefinition("Le drainage est un acte médical ou chirurgical qui consiste " +
                "à récolter des fluides ou des gaz produits par l'organisme, et à les évacuer " +
                "soit vers l'extérieur, soit vers l'intérieur du corps.");
        motGlossaire5.setRefFiche(2);

        Glossaire motGlossaire6 = new Glossaire();
        motGlossaire6.setIdTerme(6);
        motGlossaire6.setNomTerme("Lésions bulleuses");
        motGlossaire6.setDefinition("On groupe sous le nom de  Dermatoses bulleuses, les " +
                "affections dont la lésion élémentaire est une bulle. La bulle est un soulèvement" +
                " épidermique circonscrit de grande dimension (>), rempli d’un liquide qui peut " +
                "être clair ou trouble. ");
        motGlossaire6.setRefFiche(2);

        Glossaire motGlossaire7 = new Glossaire();
        motGlossaire7.setIdTerme(7);
        motGlossaire7.setNomTerme("Thoracotomie ");
        motGlossaire7.setDefinition("La thoracotomie désigne une intervention chirurgicale qui " +
                "consiste à ouvrir la cage thoracique. Cette opération se réalise lorsqu'il est " +
                "nécessaire d'intervenir sur un des organes de la cage thoracique (cœur, poumons," +
                " œsophage). On distingue plusieurs types de thoracotomie selon la zone où " +
                "s'effectue l'incision. Le chirurgien peut avoir recours à une thoracotomie avant" +
                " une chirurgie du cœur, un pontage coronarien (pose d'un conduit pour contourner" +
                " une artère coronaire) ou une pneumonectomie (ablation des poumons).");
        motGlossaire7.setRefFiche(2);

        Glossaire motGlossaire8 = new Glossaire();
        motGlossaire8.setIdTerme(8);
        motGlossaire8.setNomTerme("Bullage ");
        motGlossaire8.setDefinition("Introduire des bulles dans un liquide");
        motGlossaire8.setRefFiche(2);

        List<Glossaire> listMotGlossaire = new ArrayList<>();
        listMotGlossaire.add(motGlossaire1);
        listMotGlossaire.add(motGlossaire2);
        listMotGlossaire.add(motGlossaire3);
        listMotGlossaire.add(motGlossaire4);
        listMotGlossaire.add(motGlossaire5);
        listMotGlossaire.add(motGlossaire6);
        listMotGlossaire.add(motGlossaire7);
        listMotGlossaire.add(motGlossaire8);

        for (Glossaire listeG : listMotGlossaire) {

            EdwinDatabase.getAppDatabase(this).glossaireDao().insertGlossaire(listeG);
        }
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
                startActivity(new Intent(this, VosFichesActivity.class));
                break;
            case R.id.buttonNeedHelp:
                startActivity(new Intent(this, AideActivity.class));
                break;
            case R.id.buttonMoreInfo:
                startActivity(new Intent(this, AProposActivity.class));
                break;
            default:
                Log.d("AccueilActivityMessage", "Default value");
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
            default:
                Log.d("AccueilActivityMessage", "Default value");
                break;
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
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


/**
 private void showAProposFragment(){
 if (this.fragmentAPropos == null) this.fragmentAPropos = AProposActivity.newInstance();
 this.startTransactionFragment(this.fragmentAPropos);
 }

 **/
}
