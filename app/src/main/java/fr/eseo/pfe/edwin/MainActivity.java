package fr.eseo.pfe.edwin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fr.eseo.pfe.edwin.data.ContenuFiche;
import fr.eseo.pfe.edwin.data.DatabaseInitializer;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.FicheInformative;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Button buttonTutorial = null;
    private Button buttonAide = null;
    private SharedPreferences Prefs;
    private String TAG;
    public static final String NAME_FILE_CGU = "file_cgu.txt";//Name of the final to read for terms and conditions use
    public static final String CONDITIONS_GENERALES_D_UTILISATION = "Conditions générales d'utilisation :";
    public static final String YOUR_OWN_APPLICATION_PACKAGENAME_COM = "YOUR.OWN.APPLICATION.PACKAGENAME.COM";
    public static final String NULL_TUTORIAL = "NullTutorial";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

/*
        FicheInformative ficheInformative = new FicheInformative();

        ficheInformative.setIdFiche(1);
        ficheInformative.setNomOperation("pneumothorax");
        ficheInformative.setRefContenuFiche(1);

        ContenuFiche contenuFiche = new ContenuFiche();

        contenuFiche.setIdContenuFiche(1);
        contenuFiche.setMaladie("Vous avez présenté un décollement de plèvre appelé pneumothorax, lié à la présence anormale d’air dans l’espace pleural.\n" +
                "Ce pneumothorax est le plus fréquemment dû à la rupture de petites bulles présentes à la surface du poumon. Parfois une maladie pulmonaire est retrouvée, comme la BronchoPneumopathie Chronique Obstructive ou BPCO. Enfin, chez la femme jeune, il peut y avoir un lien avec les périodes de menstruation.\n");
        contenuFiche.setRisquesMaladie("Le risque principal du pneumothorax est la suffocation et l’arrêt cardiorespiratoire. Le passage massif d’air dans l’espace pleural peut empêcher la ventilation normale du poumon décollé, mais aussi celle de l’autre poumon.\n" +
                "Lors du premier épisode de pneumothorax chez un sujet sain par ailleurs, un simple drainage peut être proposé. Cependant, en cas de récidive, il est admis d’opérer afin d’éviter une nouvelle récidive.\n");
        contenuFiche.setPrincipe("Les buts de l’intervention sont d’éviter toute récidive, en optenant une réexpansion complète et un accolement du poumon à la paroi. La chirurgie pourra également permettre d’obtenir un diagnostic plus précis et de traiter la cause du pneumothorax.\n" +
                "-\tExploration de la cavité thoracique\n" +
                "-\tTraitement de la cause = traitement des lésions bulleuses\n" +
                "-\tSymphyse pleurale = accolement du poumon à la paroi\n" +
                "Pour réaliser cet accolement, plusieurs techniques sont envisageables et utilisables en même temps :\n" +
                "-\tirritation de la plèvre pariétale soit chimiquement par un produit irritant (talc par exemple), soit mécaniquement en frottant fort la plèvre\n" +
                "-\t« arrachage » de la plèvre pariétale, sur plusieurs cm = pleurectomie.\n" +
                "Le principe de ces 2 techniques est de déclencher une réaction inflammatoire qui permettra l’accolement du poumon à la paroi.\n");
        contenuFiche.setTechnique("De nos jours, l’utilisation d’une caméra pour visualiser l’intérieur du thorax – vidéothoracoscopie - est répandue et permet de vous opérer en évitant la thoracotomie c’est-à-dire l’ouverture plus large et l’écartement des côtes, source de douleurs post-opératoires .\n" +
                "Cependant, seul le résultat final compte et la technique utilisée sera celle qui permettra le meilleur résultat.\n");
        contenuFiche.setSuites("Vous sortirez de la salle d’opération avec un ou deux drains thoraciques. Ce sont des tuyaux qui permettront d’aspirer l’air ou les liquides produit par le thorax pendant ou après l’intervention. Ils seront retirés le plus souvent dans les 4 jours suivants l’intervention, mais il est parfois nécessaire de les laisser plus longtemps.\n" +
                "Certains poumons étant plus fragiles que d’autres, il est parfois nécessaire d’intervenir une seconde fois pour stopper les éventuelles fuites d’air persistantes.\n");
        contenuFiche.setRisquesOperation("Comme toute intervention chirurgicale, il existe des risques de douleurs post-opératoires, de saignement et d’infections. L’hospitalisation peut être prolongée de quelques jours voire une réintervention peut être nécessaire.\n" +
                "Plus spécifiquement pour cette chirurgie thoracique, un bullage du drain pendant plusieurs jours, ou un accolement incomplet du poumon peuvent apparaître. \n" +
                "Plus rarement, un écoulement de lymphe dans le drain ou des troubles au niveau de l’œil du côté opéré peuvent être retrouvés.\n");
        contenuFiche.setSuivi("Un arrêt de travail et d’activités physique d’un mois est classique.\n" +
                "Les sports tels que la plongée sous-marine, ainsi que les instruments de musique à vent (trompette, saxophone…) seront contre-indiqués à vie.\n" +
                "Il faudra attendre la consultation de contrôle avec le chirurgien pour savoir si vous pourrez reprendre l’avion.\n" +
                "Une consultation avec votre chirurgien avec radiographie des poumons à un mois est prévue.\n" +
                "Vous serez également revu par votre pneumologue afin de faire le point sur vos capacités pulmonaires.\n");

        ArrayList<FicheInformative> ficheInformativeArrayList = new ArrayList<FicheInformative>();

        ficheInformativeArrayList.add(ficheInformative);

        ArrayList<ContenuFiche> contenuFicheArrayList = new ArrayList<ContenuFiche>();

        contenuFicheArrayList.add(contenuFiche);

        DatabaseInitializer.populateAsync(EdwinDatabase.getAppDatabase(this), ficheInformativeArrayList, contenuFicheArrayList,
                new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList());
*/

        /**
         * Launch the box for the tearms and conditions
         * After, launch the Tutorial for the first time
         */
        termsAndConditionsDialog();
        /**
         * The theme correspond to the loading screen
         */
        setTheme(R.style.AppTheme);

        //AFFICHE LA VUE ACCUEIL
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //BOUTON DE REDIRECTION VERS TUTORIAL
        buttonTutorial = findViewById(R.id.tutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actualView) {
                Intent intent = new Intent(getBaseContext(), TutorialActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //BOUTON DE REDIRECTION VERS AIDE
        buttonAide = findViewById(R.id.aide);
        buttonAide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actuelView) {
                Intent intent = new Intent(getBaseContext(), AideActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        //BOUTON DE REDIRECTION VERS A PROPOS
        Button buttonAPropos = (Button) findViewById(R.id.apropos);
        buttonAPropos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actuelView) {
                Intent intent = new Intent(MainActivity.this, AProposActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS FICHES
        Button buttonFiches = (Button) findViewById(R.id.fiches);
        buttonFiches.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actuelView) {
                Intent intent = new Intent(MainActivity.this, FicheDetailsActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS GLOSSAIRE
        Button buttonGlossaire = (Button) findViewById(R.id.glossaire);
        buttonGlossaire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actuelView) {
                Intent intent = new Intent(MainActivity.this, GlossaireActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS MENU
        Button buttonMenu = (Button) findViewById(R.id.menu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actuelView) {
                Intent intent = new Intent(MainActivity.this, MenuActivityy.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS QUIZ
        Button buttonQuiz = (Button) findViewById(R.id.quiz);
        buttonQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actuelView) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Pop up to read the tearms and conditions
     * If not accepted, the pop up will be at every start of the app
     */
    private void termsAndConditionsDialog() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean agreed = sharedPreferences.getBoolean("agreed", false);
        String cguMessage = readTxtFile(NAME_FILE_CGU);

        if (!agreed) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            builder.setTitle(CONDITIONS_GENERALES_D_UTILISATION)
                    .setMessage(cguMessage)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
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
        Prefs = this.getSharedPreferences(YOUR_OWN_APPLICATION_PACKAGENAME_COM, Context.MODE_PRIVATE);
        if (promptTutorial()) {
            // Tutorial was never prompted
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }

            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Implement promptTutorial() which is called to decide if the dialog window needs to be prompted.
     * <p>
     * Basically what it does is: it checks for keyTutorial ins Shared prefs.
     * <p>
     * If it finds it there it will return false, because the dialog was already prompted at some point.
     */
    // The function that decides if you need to prompt the dialog window
    public boolean promptTutorial() {
        // Check fo saved value in Shared preference for key: keyTutorial return "NullTutorial" if nothing found
        String keyTutorial = Prefs.getString("keyTutorial", NULL_TUTORIAL);
        // Log what we found in shared preference
        Log.d(TAG, "Shared Pref read: [keyTutorial: " + keyTutorial + "]");

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
        SharedPreferences.Editor editor = Prefs.edit();
        // Log what are we saving in the shared Prefs
        Log.d(TAG, "Shared Prefs Write [" + key + ":" + value + "]");
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

}
