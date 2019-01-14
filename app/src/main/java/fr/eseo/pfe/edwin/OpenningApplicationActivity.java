package fr.eseo.pfe.edwin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.DatabaseInitializer;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;
import fr.eseo.pfe.edwin.data.Glossaire;
import fr.eseo.pfe.edwin.data.JSON;
import fr.eseo.pfe.edwin.data.Question;
import fr.eseo.pfe.edwin.data.Quiz;
import fr.eseo.pfe.edwin.launch.TutorialActivity;

public class OpenningApplicationActivity extends AppCompatActivity {
    ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private String tag;
    public static final String NAME_FILE_CGU = "file_cgu.txt";//Name of the final to read for
    // terms and conditions use
    public static final String CONDITIONS_GENERALES_D_UTILISATION = "Conditions générales " +
            "d'utilisation :";
    public static final String YOUR_OWN_APPLICATION_PACKAGENAME_COM = "YOUR.OWN.APPLICATION" +
            ".PACKAGENAME.COM";
    public static final String NULL_TUTORIAL = "NullTutorial";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * Launch the box for the tearms and conditions
         * After, launch the Tutorial for the first time
         */
        termsAndConditionsDialog();

        /**
         * The theme correspond to the loading screen
         */
        setTheme(R.style.themeTest_Launcher);
        //TODO: voir le pour le temps d'attente avec le chargement de la BDD (thread)
        SystemClock.sleep(3000); //temps d'attente de 4s


        /**
         * On charge la BDD ici avant de lancer l'accueil
         */
        //EdwinDatabase.getAppDatabase(this).clearAllTables();
        List<FicheInformative> fiches = EdwinDatabase.getAppDatabase(this).ficheInformativeDao().findAllFichesInformatives();

        if(fiches.isEmpty()){
            populateDBFirstTime();
        } else {
            updateData();
        }

        //populateDBFicheEtContenu();
        //populateDBGlossaire();
        //populateDBQuiz(); ne fonctionne pas
        //populateDBQuiz2();

        /**
         * We load the Accueil activity
         */
        Intent intent = new Intent(OpenningApplicationActivity.this, AccueilActivity.class);
        startActivity(intent);
    }


    /**
     * Pop up to read the tearms and conditions
     * If not accepted, the pop up will be at every start of the app
     */
    private void termsAndConditionsDialog() {
        final SharedPreferences defaultSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        boolean agreed = defaultSharedPreferences.getBoolean("agreed", false);
        String cguMessage = readTxtFile(NAME_FILE_CGU);

        if (!agreed) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(CONDITIONS_GENERALES_D_UTILISATION)
                    .setMessage(cguMessage)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = defaultSharedPreferences.edit();
                            editor.putBoolean("agreed", true);
                            editor.commit();
                        }
                    })
                    .setNegativeButton("Non", null)
            ;
            AlertDialog alertDialog = builder.create();
            //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            alertDialog.show();
        }
        detectFirstUseAOpenTutorialAndBox();
    }

    /**
     * Launch the tutorial for the first use of the app
     */
    private void detectFirstUseAOpenTutorialAndBox() {
        sharedPreferences = this.getSharedPreferences(YOUR_OWN_APPLICATION_PACKAGENAME_COM,
                Context.MODE_PRIVATE);
        if (promptTutorial()) {
            // Tutorial was never prompted
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style
                        .Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }

            Intent intent = new Intent(OpenningApplicationActivity.this, TutorialActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Implement promptTutorial() which is called to decide if the dialog window needs to be
     * prompted.
     * <p>
     * Basically what it does is: it checks for keyTutorial ins Shared prefs.
     * <p>
     * If it finds it there it will return false, because the dialog was already prompted at some
     * point.
     */
    // The function that decides if you need to prompt the dialog window
    public boolean promptTutorial() {
        // Check fo saved value in Shared preference for key: keyTutorial return "NullTutorial"
        // if nothing found
        String keyTutorial = sharedPreferences.getString("keyTutorial", NULL_TUTORIAL);
        // Log what we found in shared preference
        Log.d(tag, "Shared Pref read: [keyTutorial: " + keyTutorial + "]");

        if (keyTutorial.contains(NULL_TUTORIAL)) {
            // if nothing found save a new value "PROMPTED" for the key: keyTutorial
            // to save it in shared prefs just call our saveKey function
            saveKey("keyTutorial", "PROMPTED");
            return true;
        }
        // if some value was found for this key we already propted this window some time in the past
        // no need to prompt it again
        return false;
    }

    /**
     * Implement Save Key, to save values in the Android Shared Preference.
     *
     * @param key
     * @param value
     */
    // The SaveKEy function
    public void saveKey(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Log what are we saving in the shared sharedPreferences
        Log.d(tag, "Shared sharedPreferences Write [" + key + ":" + value + "]");
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * Read a text file
     *
     * @param nameFileTxt
     * @return text string
     */
    public String readTxtFile(String nameFileTxt) {
        String text = "";
        try {
            InputStream is = getAssets().open(nameFileTxt);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException exception) {
            exception.printStackTrace();

        }
        return text;
    }

    /**
     * Remplissaire de la BDD locale à partir de la BDD externe à la première ouverture de l'application
     */
    private void populateDBFirstTime() {

        try {

        ArrayList<FicheInformative> ficheInformativeArrayList = JSON.getFiches();

        ArrayList<ContenuFiche> contenuFicheArrayList = JSON.getContenuFiches();

        ArrayList<Glossaire> glossaire = JSON.getGlossaire();

        ArrayList<Quiz> quizArrayList = JSON.getQuiz();

        ArrayList<Question> questions = JSON.getQuestions();

        DatabaseInitializer.populateAsync(EdwinDatabase.getAppDatabase(this),
                    ficheInformativeArrayList,
                    contenuFicheArrayList, glossaire, quizArrayList, questions);

        ArrayList<JSON> jsonArrayList = new ArrayList<JSON>();

        JSON jsonFiches = new JSON();
        jsonFiches.setId(1);
        jsonFiches.setJson(JSON.getJSONFiches());
        jsonFiches.setContenu("fiches");
        jsonArrayList.add(jsonFiches);

        JSON jsonContenuFiche = new JSON();
        jsonContenuFiche.setId(2);
        jsonContenuFiche.setJson(JSON.getJSONContenuFiche());
        jsonContenuFiche.setContenu("contenufiche");
        jsonArrayList.add(jsonContenuFiche);

        JSON jsonGlossaire = new JSON();
        jsonGlossaire.setId(3);
        jsonGlossaire.setJson(JSON.getJSONGlossaire());
        jsonGlossaire.setContenu("glossaire");
        jsonArrayList.add(jsonGlossaire);

        JSON jsonQuiz = new JSON();
        jsonQuiz.setId(4);
        jsonQuiz.setJson(JSON.getJSONQuiz());
        jsonQuiz.setContenu("quiz");
        jsonArrayList.add(jsonQuiz);

        JSON jsonQuestions = new JSON();
        jsonQuestions.setId(5);
        jsonQuestions.setJson(JSON.getJSONQuestions());
        jsonQuestions.setContenu("questions");
        jsonArrayList.add(jsonQuestions);

        EdwinDatabase.getAppDatabase(this).jsonDao().insertJSON(jsonFiches);
        EdwinDatabase.getAppDatabase(this).jsonDao().insertJSON(jsonContenuFiche);
        EdwinDatabase.getAppDatabase(this).jsonDao().insertJSON(jsonGlossaire);
        EdwinDatabase.getAppDatabase(this).jsonDao().insertJSON(jsonQuiz);
        EdwinDatabase.getAppDatabase(this).jsonDao().insertJSON(jsonQuestions);

    } catch (Exception ex){
    }

    }

    /**
    Fonction permettant de faire un update de la BDD locale à l'ajout d'une ou plusieurs fiches à la BDD externe
     */
    private void updateData(){

        JSON jsonFiches = EdwinDatabase.getAppDatabase(this).jsonDao().findJSONFromContenu("fiches");
        JSON jsonContenuFiche = EdwinDatabase.getAppDatabase(this).jsonDao().findJSONFromContenu("contenufiche");
        JSON jsonGlossaire = EdwinDatabase.getAppDatabase(this).jsonDao().findJSONFromContenu("glossaire");
        JSON jsonQuiz = EdwinDatabase.getAppDatabase(this).jsonDao().findJSONFromContenu("quiz");
        JSON jsonQuestions = EdwinDatabase.getAppDatabase(this).jsonDao().findJSONFromContenu("questions");

        try {
            if (!((jsonFiches.getJson()).equals(JSON.getJSONFiches())) || !((jsonContenuFiche.getJson()).equals(JSON.getJSONContenuFiche())) || !((jsonGlossaire.getJson()).equals(JSON.getJSONGlossaire())) ||
                    !((jsonQuiz.getJson()).equals(JSON.getJSONQuiz())) || !((jsonQuestions.getJson()).equals(JSON.getJSONQuestions()))) {

                EdwinDatabase.getAppDatabase(this).clearAllTables();

                populateDBFirstTime();

            }
        } catch (Exception ex){
        }
    }

    private void populateDBFicheEtContenu() {
        
        FicheInformative ficheInformative = new FicheInformative();
        ficheInformative.setNomOperation("Ablation de sphincter");
        ficheInformative.setRefContenuFiche(1);
        ContenuFiche contenuFiche = new ContenuFiche();
        contenuFiche.setIntro("L’intervention qui vous est proposée s’appelle l’ablation de sphincter urinaire artificiel. ");
        contenuFiche.setRappelAnatomique("La vessie est le réservoir dans lequel l’urine provenant des reins est stockée\n" +
                "avant d’être évacuée lors de la miction.\n" +
                "La prostate est une glande située sous la vessie. Pour sortir de la vessie,\n" +
                "l'urine doit passer à travers la prostate, par le canal de l'urètre. ");
        contenuFiche.setMaladie("Un sphincter urinaire artificiel a été mis en place en raison d’une incontinence\n" +
                "urinaire. Ce sphincter artificiel est composé de 3 éléments reliés entre eux par\n" +
                "de petits tuyaux : une manchette gonflable placée autour de l’urètre, un\n" +
                "réservoir situé dans la région inguinale sous les muscles de l’abdomen et une\n" +
                "pompe de contrôle située dans une bourse.\n" +
                "Plusieurs raisons peuvent amener votre chirurgien à proposer l’ablation de\n" +
                "ce matériel : un dysfonctionnement, une infection du matériel, et une\n" +
                "érosion ou une perforation de l’urètre par la manchette sont les raisons les\n" +
                "plus fréquentes. ");
        contenuFiche.setIntro("En cas d’infection du sphincter artificiel ou de perforation de l’urètre en regard de la manchette, il n’existe pas\n" +
                "d’autre option que de retirer l’ensemble du matériel.\n" +
                "En cas de dysfon");
        contenuFiche.setRappelAnatomique("test");
        contenuFiche.setRisquesMaladie("Cette intervention se pratique par deux ouvertures de la peau, l’une au niveau du périnée et l’autre au niveau de la\n" +
                "partie basse de l’abdomen.\n" +
                "Cette intervention consiste à enlever la totalité du matériel : la manchette, le réservoir, la pompe de contrôle et les\n" +
                "tuyaux de raccordement.\n" +
                "Cette intervention nécessite en moyenne 1 à 2 jours d’hospitalisation. En cas d’infection, l’hospitalisation peut être\n" +
                "plus longue en raison de la nécessité de soins additionnels. ");
        contenuFiche.setPrincipe("Une analyse d'urines est réalisée avant l'intervention pour en vérifier la stérilité ou traiter une éventuelle infection.\n" +
                "Il peut également être décidé de réaliser un bilan sanguin notamment pour rechercher des signes infectieux et\n" +
                "étudier le fonctionnement des reins.\n" +
                "En cas d’infection avérée du matériel, un traitement antibiotique est débuté avant l’intervention.\n" +
                "Une fibroscopie de l’urètre peut être réalisée pour confirmer une éventuelle perforation de l’urètre en regard de la\n" +
                "manchette du sphincter.\n" +
                "Les médicaments anti-coagulant ou anti-agrégant doivent le plus souvent être arrêtés quelques jours avant\n" +
                "l’intervention. ");
        contenuFiche.setTechnique("Elle se déroule par voie ouverte.\n" +
                "Le chirurgien réalise une incision au niveau du périnée, en arrière des bourses, pour accéder à la manchette du\n" +
                "sphincter et la retirer. Si l’urètre est blessé, un geste de réparation peut éventuellement être réalisé lorsque les\n" +
                "conditions le permettent. Le chirurgien réalise de même une incision au niveau de l’abdomen pour accéder au\n" +
                "réservoir du sphincter et le retirer. Cette même incision lui permet d’accéder à la pompe de contrôle située dans\n" +
                "une des bourses, et de la retirer, ainsi que l’ensemble des tuyaux");
        contenuFiche.setSuites("La sonde urinaire peut être maintenue plusieurs jours, en fonction de l’état de l’urètre. Vous pouvez ressentir une\n" +
                "irritation du canal urinaire ou des sensations de faux besoins d’uriner secondaires à la présence de la sonde. Un\n" +
                "traitement antibiotique est nécessaire en cas d’infection. ");
        contenuFiche.setRisquesOperation("Dans la majorité des cas, l’intervention qui vous est proposée se déroule sans complication. Cependant, tout acte\n" +
                "chirurgical comporte un certain nombre de risques et complications décrits ci-dessous :\n" +
                "® Certaines complications sont liées à votre état général et à l’anesthésie ; elles vous seront expliquées lors de la\n" +
                "consultation pré-opératoire ");
        contenuFiche.setSuivi("Il est rappelé que toute intervention chirurgicale comporte un certain nombre de risques y compris vitaux, tenant à\n" +
                "des variations individuelles qui ne sont pas toujours prévisibles. Certaines de ces complications sont de survenue\n" +
                "exceptionnelle (plaies des vaisseaux, des nerfs et de l’appareil digestif) et peuvent parfois ne pas être guérissables.\n" +
                "Au cours de cette intervention, le chirurgien peut se trouver en face d’une découverte ou d’un événement imprévu\n" +
                "nécessitant des actes complémentaires ou différents de ceux initialement prévus, voire une interruption du protocole\n" +
                "prévu.");

        FicheInformative ficheInformative2 = new FicheInformative();
        ficheInformative2.setNomOperation("Adenoctomoie prostatique chirurgicale");
        ficheInformative2.setRefContenuFiche(2);
        ficheInformative2.setIdFiche(2);
        ContenuFiche contenuFiche2 = new ContenuFiche();
        contenuFiche2.setIdContenuFiche(2);
        contenuFiche2.setIntro("L’intervention proposée a pour objectif de traiter l’adénome de prostate que\n" +
                "vous présentez, par voie chirurgicale dite « ouverte ». ");
        contenuFiche2.setRappelAnatomique("La vessie est le réservoir dans lequel l’urine provenant des reins est stockée\n" +
                "avant d’être évacuée lors de la miction.\n" +
                "La prostate est une glande située sous la vessie. Pour sortir de la vessie,\n" +
                "l'urine doit traverser la prostate, par la canal de l'urètre.\n" +
                "L’urètre est le canal par lequel les urines sont expulsées de la vessie. ");
        contenuFiche2.setMaladie("L'augmentation de volume de la prostate,\n" +
                "adénome prostatique ou hypertrophie bénigne prostatique (HBP) est une\n" +
                "pathologie de la partie centrale de la prostate. Elle peut avoir comme\n" +
                "conséquence l'apparition progressive d'une gêne à l'évacuation de la vessie\n" +
                "ou des envies fréquentes d’uriner et d’autres complications (lithiase,\n" +
                "hématurie, rétention d’urines…).\n" +
                "Une intervention chirurgicale est indiquée lorsque le traitement médical\n" +
                "n’est plus efficace et dans le cas de complications. ");
        contenuFiche2.setRisquesMaladie("L’intervention peut être réalisée par voie naturelle, trans-urétrale sous contrôle endoscopique. Appelée résection\n" +
                "trans-urétrale de la prostate (RTUP), elle est proposée lorsque le traitement médical n’est plus suffisamment\n" +
                "efficace ou lorsqu’une complication apparaît. Elle consiste à enlever l’adénome prostatique pour élargir le canal\n" +
                "urinaire. Votre chirurgien vous a expliqué pourquoi il privilégie une adénomectomie chirurgicale plutôt qu’une\n" +
                "intervention endoscopique, le volume de la prostate est un des critères de choix importants. ");
        contenuFiche2.setPrincipe("Une analyse d'urines est prescrite avant l'intervention pour en vérifier la stérilité ou traiter une éventuelle infection.\n" +
                "Une infection urinaire non traitée conduit à différer la date de votre opération.\n" +
                "Un bilan sanguin, comportant l’étude de la fonction des reins, est réalisé avant l’intervention.\n" +
                "la prise d’anti-agrégant plaquettaire ou d’anticoagulant nécessite d’être arrêtée pendant plusieurs jours ou\n" +
                "éventuellement poursuivie à faible dose pour l’aspirine. ");
        contenuFiche2.setTechnique("L’adénomectomie prostatique chirurgicale consiste à retirer la totalité de l’adénome, c’est-à-dire la partie centrale\n" +
                "de la prostate, en passant au travers de la vessie ou directement par incision de la capsule prostatique. La prostate\n" +
                "périphérique est laissée en place.\n" +
                "Elle nécessite une incision cutanée de quelques centimètres au dessus du pubis.\n" +
                "Dans la technique trans-vésicale, la vessie, située sous la paroi musculaire de l’abdomen, est ouverte. Le chirurgien\n" +
                "dissèque l’adénome et le sépare du reste de la prostate en passant par la vessie. Le tissu prostatique retiré est\n" +
                "conservé pour être analysé au microscope.\n" +
                "En fin d’intervention, un drain aspiratif est placé au niveau de la zone opératoire. Une sonde vésicale, mise en place\n" +
                "pendant l’intervention, est maintenue pendant plusieurs jours suivant les recommandations de votre urologue.\n" +
                "Elle peut permettre de laver la vessie de façon continue avec du sérum pour éviter la formation de caillots de sang\n" +
                "et l’obstruction de la sonde. ");
        contenuFiche2.setSuites("Le délai pour l’arrêt du lavage de la vessie et le retrait de la sonde est variable, habituellement de quelques jours et il\n" +
                "est décidé au cas par cas par votre chirurgien. Lorsque des caillots sanguins obstruent la sonde, un lavage de vessie\n" +
                "avec une seringue à gros embout est utilisé pour rétablir la perméabilité de la sonde. ");
        contenuFiche2.setRisquesOperation("La consultation post-opératoire intervient dans les semaines qui suivent l’intervention. Le suivi consiste à évaluer\n" +
                "l’amélioration de vos symptômes urinaires et la bonne qualité de la vidange vésicale.\n" +
                "Une analyse d’urine à la");
        contenuFiche2.setSuivi("Dans la majorité des cas, l’intervention qui vous est proposée se déroule sans complication. Cependant, tout acte\n" +
                "chirurgical comporte un certain nombre de risques et complications décrits ci-dessous :\n" +
                "® Certaines complications sont liées à votre état général et à l’anesthésie ; elles vous seront expliquées lors de la\n" +
                "consultation pré-opératoire avec le médecin anesthésiste ou le chirurgien et sont possibles dans toute \n" +
                "* L’Association Française d’Urologie\n" +
                "n’assume aucune responsabilité\n" +
                "propre en ce qui concerne les\n" +
                "conséquences dommageables\n" +
                "éventuelles pouvant résulter de\n" +
                "l’exploitation des données extraites\n" +
                "des documents, d’une erreur ou\n" +
                "d’une imprécision dans le contenu\n" +
                "des documents.\n" +
                "Votre urologue se tient à votre disposition pour tout renseignement.\n" +
                "intervention chirurgicale.\n" +
                "® Les complications directement en relation avec l’intervention sont rares, mais possibles ");

        FicheInformative ficheInformative3 = new FicheInformative();
        ficheInformative3.setNomOperation("Auto sondage urinaire");
        ficheInformative3.setRefContenuFiche(3);
        ficheInformative3.setIdFiche(3);
        ContenuFiche contenuFiche3 = new ContenuFiche();
        contenuFiche3.setIdContenuFiche(3);
        contenuFiche3.setIntro("Le but de l’auto-sondage est d’assurer une vidange régulière de la vessie\n" +
                "grâce à l'introduction par vous même d'une sonde par l'urètre. Il est indiqué\n" +
                "lorsque le mauvais fonctionnement de la vessie ne permet plus une\n" +
                "évacuation naturelle et facile des urines. Il convient alors de faire environ 4 à\n" +
                "6 autosondages par jour et de ne plus avoir de miction spontanée entre les\n" +
                "sondages. ");
        contenuFiche3.setRappelAnatomique("L’objectif du changement de mode de vidange vésicale (façon d’uriner) au\n" +
                "profit des auto-sondages est de vider correctement et totalement votre\n" +
                "vessie.\n" +
                "Le fonctionnement de votre vessie a été évalué à travers les différents\n" +
                "examens que votre médecin vous a fait pratiquer. Si vous ne videz pas votre\n" +
                "vessie dans sa totalité ou bien si votre vessie est obligée de trop travailler\n" +
                "pour se vider, il existe des risques importants d’infection urinaire, d’altération\n" +
                "rénale voire d’insuffisance rénale. Rapidement vous pouvez avoir des\n" +
                "complications graves liées à ces problèmes urinaires. ");
        contenuFiche3.setMaladie("Lors d’une consultation, une hospitalisation de jour ou de semaine, la technique des auto-sondages va vous être\n" +
                "enseignée. Vous apprenez à vous sonder en position assise et en position couchée afin que vous puissiez vous sonder\n" +
                "n’importe où, même en dehors de chez vous : dans des toilettes publiques, au travail, chez des amis… Vous essayerez\n" +
                "plusieurs types de sondes afin de trouver celle qui vous convient le mieux. ");
        contenuFiche3.setRisquesMaladie("Non : la technique des auto-sondages est indolore grâce aux évolutions des matériels de sondage. Lors de\n" +
                "l’apprentissage, on vous montre toute la palette des sondes disponibles et on vous guide dans le choix du matériel le\n" +
                "plus adapté à votre cas. ");
        contenuFiche3.setPrincipe("Le fait d’avoir une vessie qui ne se vide pas bien expose automatiquement à une plus grande concentration de\n" +
                "bactéries dans votre vessie par rapport à une vessie qui se vide normalement. Cette présence de bactéries vous\n" +
                "expose, plus que la population normale, à avoir des infections urinaires (avec des symptômes ou de la fièvre).\n" +
                "Les auto-sondages permettent de faire en sorte que ces épisodes d’infections urinaires graves soient\n" +
                "considérablement diminués. En revanche, vous aurez toujours des germes présents dans les urines, mais ils ne\n" +
                "resteront pas suffisamment longtemps dans la vessie pour entraîner des symptômes mettant votre santé en jeu. ");
        contenuFiche3.setTechnique("L’Association Française d’Urologie\n" +
                "n’assume aucune responsabilité\n" +
                "propre en ce qui concerne les\n" +
                "conséquences dommageables\n" +
                "éventuelles pouvant résulter de\n" +
                "l’exploitation des données extraites\n" +
                "des documents, d’une erreur ou\n" +
                "d’une imprécision ");
        contenuFiche3.setSuites("Lors d’une consultation, une hospitalisation de jour ou de semaine, la technique des auto-sondages va vous être\n" +
                "enseignée. Vous apprenez à vous sonder en position assise et en position couchée afin que vous puissiez vous sonder\n" +
                "n’importe où, même en dehors de chez vous : dans des toilettes publiques, au travail, chez des amis… Vous essayerez\n" +
                "plusieurs types de sondes afin de trouver celle qui vous convient le mieux. ");
        contenuFiche3.setRisquesOperation("Le but est que vous sachiez vous sonder seul, dans n’importe quel endroit, à l’aide du type de sonde que vous aurez\n" +
                "choisi. ");
        contenuFiche3.setSuivi("Le fait d’avoir une vessie qui ne se vide pas bien expose automatiquement à une plus grande concentration de\n" +
                "bactéries dans votre vessie par rapport à une vessie qui se vide normalement. Cette présence de bactéries vous\n" +
                "expose, plus que la population normale, à avoir des i");

        FicheInformative ficheInformative4 = new FicheInformative();
        ficheInformative4.setNomOperation("Ballonets latero uretraux");
        ficheInformative4.setRefContenuFiche(4);
        ficheInformative4.setIdFiche(4);
        ContenuFiche contenuFiche4 = new ContenuFiche();
        contenuFiche4.setIdContenuFiche(4);
        contenuFiche4.setIntro("L’intervention qui vous est proposée a pour objectif de corriger\n" +
                "l’incontinence urinaire par implantation de ballons péri-urétraux. ");
        contenuFiche4.setRappelAnatomique("L’incontinence urinaire d’effort est une des conséquences possibles de la\n" +
                "chirurgie de la prostate. Elle peut apparaître après une prostatectomie totale\n" +
                "pour cancer de la prostate, ou plus rarement à la suite d’une intervention\n" +
                "pour adénome de prostate (adénomectomie par voie haute ou résection de\n" +
                "la prostate par voie endoscopique). ");
        contenuFiche4.setMaladie("A la suite d’une prostatectomie totale, l’anatomie de la région sous vésicale est\n" +
                "profondément modifiée (figure 1), le sphincter strié, externe assurant seul le\n" +
                "maintien de la continence. Les fuites urinaires peuvent se produire quand ce\n" +
                "sphincter externe n’est pas assez efficace du fait d’une élévation brutale de la\n" +
                "pression dans la vessie, lors des efforts (toux, rire, activités sportives…).\n" +
                "L’incontinence d’effort peut aussi survenir après\n" +
                "une chirurgie prostatique réalisée pour un\n" +
                "adénome. La perte d’efficacité du sphincter externe, affaib");
        contenuFiche4.setRisquesMaladie("Le système des ballonnets latéro-urétraux (système Pro-ACT™) repose sur\n" +
                "le principe d’une compression passive extrinsèque de la lumière urétrale\n" +
                "par deux ballonnets implantés par voie périnéale, en arrière des bourses,\n" +
                "sous contrôle radiologique.");
        contenuFiche4.setPrincipe("Après échec de la rééducation périnéo-sphinctérienne, qui est un des moyens pour renforcer le plancher pelvien et\n" +
                "réduire l’incontinence, une solution chirurgicale peut être envisagée pour des fuites urinaires persistant plus de 9\n" +
                "mois après la prostatectomie. ");
        contenuFiche4.setTechnique("Avant l’intervention, votre situation peut être évaluée par différents examens :\n" +
                "® Estimation de l’importance des pertes d’urines a l’effort.\n" +
                "® Bilan urodynamique.\n" +
                "® Urethrocystoscopie pour vérifier l’intégrité du canal urétral et de la vessie.\n" +
                "® Questionnaire d’évaluation des symptômes et de la gêne occasionnée.\n" +
                "® Examen cytobactériologique des urines (ECBU), l’intervention n’est réalisée qu’en présence d’urines stériles.\n" +
                "Le résultat en est communiqué à votre médecin et à votre chirurgien. ");
        contenuFiche4.setSuites("Après mise en place d’une sonde urinaire,\n" +
                "le chirurgien réalise une à deux petites\n" +
                "incisions périnéales, en arrière des\n" +
                "bourses, de façon à introduire un guide\n" +
                "métallique parallèle à l’urètre.\n" +
                "Apres vérification (en effectuant si besoin\n" +
                "une radiographie au bloc opératoire) de la\n" +
                "bonne position de l’introducteur, le\n" +
                "ballonnet est mis en place. Une\n" +
                "endoscopie est réalisée pour vérifier le\n" +
                "positionnement correct du ballon. La\n" +
                "procédure est répétée pour le deuxième\n" +
                "ballon.");
        contenuFiche4.setRisquesOperation("La sonde urinaire, présente à votre réveil, est conservée au moins pendant 24 heures. Après le retrait de sonde, le jet\n" +
                "urinaire peut être un peu plus faible qu’auparavant. Il se peut qu’initialement, vous constatiez une persistance des\n" +
                "fuites à l’effort, ce qui est normal si les ballons mis en place ont été peu remplis, de manière à mieux cicatriser.\n" +
                "Un traitement antalgique vous est prescrit si besoin. Un traitement anticoagulant peut être effectué pour la prévention\n" +
                "de la thrombose veineuse.");
        contenuFiche4.setSuivi("La sonde urinaire, présente à votre réveil, est conservée au moins pendant 24 heures. Après le retrait de sonde, le jet\n" +
                "urinaire peut être un peu plus faible qu’auparavant. Il se peut qu’initialement, vous constatiez une persistance des\n" +
                "fuites à l’effort, ce qui est normal si les ballons mis en place ont été peu remplis, de manière à mieux cicatriser.\n" +
                "Un traitement antalgique vous est prescrit si besoin. Un traitement anticoagulant peut être effectué pour la prévention\n" +
                "de la thrombose veineuse.");

        FicheInformative ficheInformative5 = new FicheInformative();
        ficheInformative5.setNomOperation("Bandelette de pro");
        ficheInformative5.setRefContenuFiche(5);
        ficheInformative5.setIdFiche(5);
        ContenuFiche contenuFiche5 = new ContenuFiche();
        contenuFiche5.setIdContenuFiche(5);
        contenuFiche5.setIntro("Le but de l’intervention, qui vous est proposée, est de traiter l’incontinence\n" +
                "urinaire par la mise en place d’une bandelette sous urétrale. ");
        contenuFiche5.setRappelAnatomique("L’incontinence urinaire d’effort est une des conséquences possibles de la\n" +
                "chirurgie de la prostate. Elle peut apparaître après une prostatectomie totale\n" +
                "pour cancer de la prostate, ou plus rarement à la suite d’une intervention\n" +
                "pour adénome de prostate (adénomectomie par voie haute ou résection de\n" +
                "la prostate par voie endoscopique). ");
        contenuFiche5.setMaladie("A la suite d’une prostatectomie totale, l’anatomie de la région sous vésicale est\n" +
                "profondément modifiée (figure 1), le sphincter strié, externe assurant seul le\n" +
                "maintien de la continence.\n" +
                "Les fuites urinaires peuvent se produire quand\n" +
                "ce sphincter externe n’est pas assez efficace du fait d’une élévation brutale de la\n" +
                "pression dans la vessie, lors des efforts (toux, rire, activités sportives…)");
        contenuFiche5.setRisquesMaladie("Différents types de bandelettes tran-sobturatrices avec différents systèmes de pose sont commercialisés,\n" +
                "partageant tous les principes décrits ci-dessus. Votre chirurgien choisit la bandelette la mieux adaptée à votre cas et\n" +
                "à son expérience");
        contenuFiche5.setPrincipe("Après échec de la rééducation périnéo-sphinctérienne, qui est un des moyens pour renforcer le plancher pelvien et\n" +
                "réduire l’incontinence, une solution chirurgicale peut être envisagée pour des fuites urinaires persistant plus de 9\n" +
                "mois après la prostatectomie");
        contenuFiche5.setTechnique("Après échec de la rééducation périnéo-sphinctérienne, qui est un des moyens pour renforcer le plancher pelvien et\n" +
                "réduire l’incontinence, une solution chirurgicale peut être envisagée pour des fuites urinaires persistant plus de 9\n" +
                "mois après la prostatectomie");
        contenuFiche5.setSuites("Après échec de la rééducation périnéo-sphinctérienne, qui est un des moyens pour renforcer le plancher pelvien et\n" +
                "réduire l’incontinence, une solution chirurgicale peut être envisagée pour des fuites urinaires persistant plus de 9\n" +
                "mois après la prostatectomie");
        contenuFiche5.setRisquesOperation("La consultation d’anesthésie est également obligatoire avant l’intervention. Vous serez informé des modalités de\n" +
                "l’anesthésie générale ou locorégionale et des risques de la procédure d’a");
        contenuFiche5.setSuivi("® Une hospitalisation de quelques jours.\n" +
                "® Une antibioprophylaxie systématique pour prévenir l’infection.\n" +
                "® Une prévention de la thrombose veineuse par injection d’anticoagulants. ");

        FicheInformative ficheInformative6 = new FicheInformative();
        ficheInformative6.setNomOperation("Encore une bandelette");
        ficheInformative6.setRefContenuFiche(6);
        ficheInformative6.setIdFiche(6);
        ContenuFiche contenuFiche6 = new ContenuFiche();
        contenuFiche6.setIdContenuFiche(6);
        contenuFiche6.setIntro("L’incontinence urinaire se définit par toute fuite involontaire d’urine à\n" +
                "l’origine d’une gène. Il existe plusieurs types d’incontinence urinaire ");
        contenuFiche6.setRappelAnatomique("L’incontinence urinaire d’effort : il se produit des fuites lors de\n" +
                "l’effort (activités sportives, toux, rire, éternuement, marche,\n" +
                "changement de position).\n" +
                "® L’incontinence urinaire par urgenturie (ou impériosités) : elle se\n" +
                "traduit par des besoins urgents qu’il n’est pas possible de retenir.\n" +
                "® L’incontinence urinaire mix");
        contenuFiche6.setMaladie("D’une faiblesse des muscles du périnée (périnée : partie du corps\n" +
                "fermant en bas le petit bassin et traversée par la terminaison des voies\n" +
                "urinaires, génitales et digestives) chargés de soutenir la vessie et\n" +
                "l’urètre. ");
        contenuFiche6.setRisquesMaladie("La femme est volontiers sujette à ce handicap\n" +
                "car son urètre est très court, son sphincter peu puissant, son périnée fragilisé\n" +
                "par plusieurs orifices permettant le passage de l’urètre, du vagin et du rectum.\n" +
                "Son périnée est soumis à rude épreuve lors de la grossesse et surtout de\n" +
                "l’accouchement. ");
        contenuFiche6.setPrincipe("Lorsque la rééducation périnéale a échoué ou que l’incontinence à l’effort est très importante, l’intervention est\n" +
                "actuellement un des moyens les plus performants pour faire disparaître durablement les fuites à l’effort.\n" +
                "Aucun médicament n’est actuellement actif sur l’incontinence urinaire d'effort");
        contenuFiche6.setTechnique("Elle consiste à positionner sous l’urètre une petite bandelette en matériel synthétique. Cette bandelette, telle un\n" +
                "hamac, restera sous l’urètre, le soutenant lors de l’effort afin d’empêcher les fuites. Différentes bandelettes avec\n" +
                "différents systèmes de pose sont commercialisées. Votre chirurgien choisira celle qui est la mieux adaptée à votre\n" +
                "cas et à son expérience. ");
        contenuFiche6.setSuites("Avant l’intervention : comme pour toute intervention chirurgicale, une consultation d’anesthésie a lieu quelques\n" +
                "jours avant l’intervention. Le choix de l’anesthésie : anesthésie locale, anesthésie locorégionale (seule la partie\n" +
                "inférieure du corps est endormie) ou anesthésie générale (vous dormez complètement) est effectué par le\n" +
                "chirurgien et le médecin anesthésiste en fonction de votre dossier et en tenant compte de votre avis");
        contenuFiche6.setRisquesOperation("L’intervention a lieu après s’être assuré, par une analyse d’urines récente, que vous n’avez pas d’infection urinaire.\n" +
                "En cas d’infection, votre intervention est différée jusqu’à stérilisation des urines.");
        contenuFiche6.setSuivi("En fin d’intervention, peuvent être mis en place une sonde dans la vessie et un tampon dans le vagin. La durée de\n" +
                "l’intervention est de 20 à 30 minutes. ");

        FicheInformative ficheInformative7 = new FicheInformative();
        ficheInformative7.setNomOperation("Immuno test");
        ficheInformative7.setRefContenuFiche(7);
        ficheInformative7.setIdFiche(7);
        ContenuFiche contenuFiche7 = new ContenuFiche();
        contenuFiche7.setIdContenuFiche(7);
        contenuFiche7.setIntro("L’intervention qui vous est proposée est destinée à traiter l’incontinence\n" +
                "urinaire, que vous présentez. ");
        contenuFiche7.setRappelAnatomique("La vessie est le réservoir dans lequel l’urine provenant des reins est stockée.\n" +
                "L’urètre est le canal d’expulsion de l’urine vers l’extérieur.\n" +
                "Le sphincter permet de fermer l’urètre et assure la continence.\n" +
                "L’action d’uriner s’appelle la miction. ");
        contenuFiche7.setMaladie("L’incontinence urinaire se définit par toute fuite involontaire d’urine à\n" +
                "l’origine d’une gène. Il existe plusieurs types d’incontinence urinaire :\n" +
                "® L’incontinence urinaire d’effort : il se produit des fuites lors de\n" +
                "l’effort (activités sportives, toux, rire, éternuement, marche,\n" +
                "changement de position). ");
        contenuFiche7.setRisquesMaladie("La femme est volontiers sujette à ce handicap car son urètre est très court, son sphincter peu puissant, son périnée\n" +
                "fragilisé par plusieurs orifices permettant le passage de l’urètre, du vagin et du rectum. Son périnée est soumis à\n" +
                "rude épreuve lors de la grossesse et surtout de l’accouchement. Enfin, le manque d’hormones après la ménopause\n" +
                "conduit à un assèchement des tissus et à une fragilité supplémentaire. ");
        contenuFiche7.setPrincipe("es fuites urinaires à l’effort sont la conséquence de l’altération des structures de soutien de la vessie et de l’urètre.\n" +
                "L’intervention type TVT consiste à positionner sous l’urèthre par voie rétropubienne (en arrière du pubis) une\n" +
                "bandelette synthétique en polypropylène qui permet de remplacer les structures de soutien défaillantes.\n" +
                "Le traitement de votre incontinence urinaire n’est pas une nécessité vitale. En fonction de votre gêne, vous décidez\n" +
                "avec votre urologue de l’intérêt de cette intervention. ");
        contenuFiche7.setTechnique("La rééducation périnéo-sphinctérienne peut permettre de pallier vos fuites urinaires. L’intervention peut vous être\n" +
                "proposée en cas d’échec de cette rééducation. Il existe d’autres modèles de bandelettes sous-urétrales placées\n" +
                "différemment par voie transobturatrice ou fixées différemment. Le choix est fait par votre urologue en fonction de\n" +
                "chaque cas. ");
        contenuFiche7.setSuites("Un bilan urodynamique peut être réalisé en préopératoire.\n" +
                "Le choix du type d’anesthésie (générale, locorégionale ou locale) dépend de l’avis du chirurgien et du médecin\n" +
                "anesthésiste. Une consultation d’anesthésie préopératoire est nécessaire quelques jours avant l’opération.\n" +
                "Une analyse d’urine est réalisée avant l’intervention. En cas d’infection urinaire, l’intervention est différée le temps\n" +
                "d’obtenir la stérilisation des urines. Une tonte de la région pubienne et vulvaire est effectuée et une douche\n" +
                "bétadinée préopératoire vous est demandée");
        contenuFiche7.setRisquesOperation("Au bloc opératoire, vous êtes installée en position gynécologique :\n" +
                "une courte incision est pratiquée sur la paroi du vagin juste en\n" +
                "dessous de l’urèthre.\n" +
                "Deux courtes incisions au dessus du pubis permettent le passage de\n" +
                "la bandelette. Celle-ci est passée au moyen d’une aiguille de\n" +
                "chaque côté de l’urètre et devant la vessie, puis elle est posée sans\n" +
                "tension sous le canal de");
        contenuFiche7.setSuivi("La rééducation périnéo-sphinctérienne peut permettre de pallier vos fuites urinaires. L’intervention peut vous être\n" +
                "proposée en cas d’échec de cette rééducation. Il existe d’autres modèles de bandelettes sous-urétrales placées\n" +
                "différemment par voie transobturatrice ou fixées différemment. Le choix est fait par votre urologue en fonction de\n" +
                "chaque cas. ");

        FicheInformative ficheInformative8 = new FicheInformative();
        ficheInformative8.setNomOperation("Biopsie de vessie");
        ficheInformative8.setRefContenuFiche(8);
        ficheInformative8.setIdFiche(8);
        ContenuFiche contenuFiche8 = new ContenuFiche();
        contenuFiche8.setIdContenuFiche(8);
        contenuFiche8.setIntro("L'intervention qui vous est proposée est destinée à réaliser un ou plusieurs\n" +
                "prélèvements de votre vessie (biopsie vésicale) et à en faire pratiquer\n" +
                "l'analyse au microscope. ");
        contenuFiche8.setRappelAnatomique("La vessie est le réservoir dans lequel l'urine provenant des reins est stockée\n" +
                "avant d'être évacuée lors de la miction. ");
        contenuFiche8.setMaladie("Une anomalie de votre vessie a été détectée ou est\n" +
                "suspectée par des examens radiologiques,\n" +
                "biologiques ou endoscopiques ; seul l'examen au microscope du tissu prélevé\n" +
                "fera le diagnostic exact permettant de vous proposer le traitement et le suivi\n" +
                "adaptés. L'absence de diagnostic précis et de traitement vous expose au\n" +
                "risque de laisser évoluer une lésion dan");
        contenuFiche8.setRisquesMaladie("Avant chaque intervention chirurgicale, une consultation d’anesthésie pré-opératoire est nécessaire. Signalez à\n" +
                "votre urologue et à l’anesthésiste vos antécédents médicaux, chirurgicaux et traitements en cours, en particulier\n" +
                "anticoaguants (aspirine, clopidogrel ,anti vitamine K) dont l’utilisation augmente le risque de saignement lors de\n" +
                "l’intervention, mais dont l’arrêt expose à des risques de thrombose (coagulation) des vaisseaux. Le traitement\n" +
                "anticoagulant est adapté et éventuellement modifié avant l’intervention. Indiquez aussi toute notion d’allergie.\n" +
                "Un antibiotique peut être administré avant l’interventio");
        contenuFiche8.setPrincipe("L’intervention se déroule sous anesthésie générale ou loco-régionale. Le chirurgien introduit dans le canal de\n" +
                "l'urètre un appareil endoscopique (cystoscope) permettant d'inspecter votre vessie et de réaliser des\n" +
                "prélèvements. Il est parfois nécessaire de dilater le canal de l'urètre afin d'introduire le cystoscope et permettre les\n" +
                "prélèvements.\n" +
                "En fonction de l'aspect de votre vessie, des prélèvements plus importants peuvent être nécessaires (résection transurétrale de la vessie). Les tissus prélevés sont envoyés au laboratoire pour analyse. ");
        contenuFiche8.setTechnique("Si une sonde a été mise en place, le lavage vésical est arrêté dès que les urines sont claires. La sonde urinaire est\n" +
                "enlevée selon les indications du chirurgien après un ou plusieurs jours. A l'ablation de la sonde, les urines sont\n" +
                "claires ou parfois encore teintées de sang. Vous pouvez ressentir des brûlures en urinant pendant quelques jours.\n" +
                "La durée de votre hospitalisation est variable, décidée par votre chirurgien en fonction des suites opératoires, de\n" +
                "votre état général et du type et de l'importance de votre lésion vésicale. ");
        contenuFiche8.setSuites("Le résultat de l'analyse du prélèvement de vessie est connu quelques jours après l'opération. Il est transmis à votre\n" +
                "médecin traitant. ");
        contenuFiche8.setRisquesOperation("Après l'opération, vous pouvez ressentir pendant quelques jours ou semaines des brûlures en urinant. Il vous est\n" +
                "recommandé de boire abondamment et il est préférable pendant cette période d'éviter des efforts importants. Un\n" +
                "saignement dans les urines est possible pendant les premières semaines postopératoires. Si ce saignement est\n" +
                "important ou entraîne des difficultés pour uriner, une nouvelle hospitalisation avec pose de sonde et lavage de la\n" +
                "vessie peut être nécessaire. ");
        contenuFiche8.setSuivi("Dans la majorité des cas, l’intervention qui vous est proposée se déroule sans complication. Cependant, tout acte\n" +
                "chirurgical comporte un certain nombre de risques et complications décrits ci-dessous ");

        FicheInformative ficheInformative9 = new FicheInformative();
        ficheInformative9.setNomOperation("Biopsie echoguidee de la prostate");
        ficheInformative9.setRefContenuFiche(9);
        ficheInformative9.setIdFiche(9);
        ContenuFiche contenuFiche9 = new ContenuFiche();
        contenuFiche9.setIdContenuFiche(9);
        contenuFiche9.setIntro("L’examen qui vous est proposé a pour objectif de réaliser des prélèvements\n" +
                "de votre prostate pour en faire l’analyse au microscope, afin de rechercher si\n" +
                "l’anomalie constatée au toucher rectal ou sur le dosage du PSA est en\n" +
                "relation avec un cancer de la prostate. Attention, une biopsie négative ne\n" +
                "peut cependant pas exclure définitivement l’existence d’un cancer. ");
        contenuFiche9.setRappelAnatomique("La prostate est sous la dépendance de l’hormone masculine appelée\n" +
                "testostérone. C’est une glande située sous la vessie et avant du rectum. Elle\n" +
                "est traversée par le canal de l’urètre, qui permet l’évacuation de l’urine. Elle\n" +
                "est proche à la fois du système sphinctérien, qui assure la continence urinaire\n" +
                "et des nerfs de l’érection. Les vésicules séminales, q");
        contenuFiche9.setMaladie("Une anomalie de votre prostate a été détectée par le toucher rectal ou la\n" +
                "valeur de PSA sanguin est considérée comme élevée. Différentes affections\n" +
                "de la prostate peuvent correspondre à ces anomalies : une infection ou\n" +
                "inflammation, une hypertrophie bénigne de la prostate ou un cancer de la\n" +
                "prostate. ");
        contenuFiche9.setRisquesMaladie("L’examen qui vous est proposé est le seul moyen d’analyser le tissu prostatique dans votre situation");
        contenuFiche9.setPrincipe("Les biopsies sont réalisées en soins externes ou plus rarement en hospitalisation ambulatoire . Une possible\n" +
                "infection urinaire est éliminée avant l’examen par un interrogatoire ciblé et au moindre doute ou facteur de risque\n" +
                "par bandelette urinaire ou examen bactériologique des urines (ECBU). Il n’est pas nécessaire de réaliser d’examen\n" +
                "sanguin de la coagulation en l’absence de facteur de risque hémorragique particulier.\n" +
                "Une préparation intestinale par un lavement rectal évacuateur peut être demandée afin de vider le rectum des\n" +
                "selles. Il n’est pas nécessaire d’être à jeun pour cet examen sauf s’il doit être réalisé sous anesthésie loco-régionale\n" +
                "ou générale.");
        contenuFiche9.setTechnique("L’urologue introduit la sonde d’échographie par l’anus. Celle-ci est protégée, lubrifiée et équipée d’un système de\n" +
                "visée. L’inconfort lié à la sonde d’échographie passant dans l’anus est modéré et supportable.\n" +
                "L’urologue réalise ensuite l‘anesthésie locale et les biopsies (en moyenne 12 prélèvements). L’aiguille est actionnée\n" +
                "par un mécanisme de déclenchement qui produit un claquement sec. Ce bruit peut vous surprendre la première\n" +
                "fois, mais la ponction elle-même est habituellement peu douloureuse. ");
        contenuFiche9.setSuites("Lorsque la série de ponctions est terminée, il peut vous être recommandé de rester allongé quelques minutes, car\n" +
                "un lever brutal pourrait provoquer un malaise. ");
        contenuFiche9.setRisquesOperation("PNEUMO Risques op");
        contenuFiche9.setSuivi("Lorsque la série de ponctions est terminée, il peut vous être recommandé de rester allongé quelques minutes, car\n" +
                "un lever brutal pourrait provoquer un malaise. ");

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

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative3);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche3);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative4);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche4);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative5);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche5);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative6);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche6);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative7);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche7);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative8);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche8);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative
                (ficheInformative9);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche
                (contenuFiche9);
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
        Quiz quiz = new Quiz(2, "Quiz culture générale - Santé et médecine N°1", 2);
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


}
