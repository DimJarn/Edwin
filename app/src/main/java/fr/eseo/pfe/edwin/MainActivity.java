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

import java.io.IOException;
import java.io.InputStream;

import fr.eseo.pfe.edwin.FirstLaunch.TutorialActivity;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    private SharedPreferences sharedPreferences;
    private String tag;
    public static final String NAME_FILE_CGU = "file_cgu.txt";//Name of the final to read for terms and conditions use
    public static final String CONDITIONS_GENERALES_D_UTILISATION = "Conditions générales d'utilisation :";
    public static final String YOUR_OWN_APPLICATION_PACKAGENAME_COM = "YOUR.OWN.APPLICATION.PACKAGENAME.COM";
    public static final String NULL_TUTORIAL = "NullTutorial";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

/*
        FicheInformative ficheInformative = new FicheInformative();

        ficheInformative.setNomOperation("gastro");
        ficheInformative.setRefContenuFiche(1);

        ContenuFiche contenuFiche = new ContenuFiche();

        contenuFiche.setMaladie("MALADIE MALADIE");
        contenuFiche.setRisquesMaladie("RISQUES");
        contenuFiche.setPrincipe("Principe");
        contenuFiche.setTechnique("Technique");
        contenuFiche.setSuites("Suites");
        contenuFiche.setRisquesOperation("Risques op");
        contenuFiche.setSuivi("Suivi");

        FicheInformative ficheInformative2 = new FicheInformative();

        ficheInformative2.setNomOperation("pneumo");
        ficheInformative2.setRefContenuFiche(2);

        ContenuFiche contenuFiche2 = new ContenuFiche();

        contenuFiche2.setMaladie("PNEUMO MALADIE");
        contenuFiche2.setRisquesMaladie("PNEUMO RISQUES");
        contenuFiche2.setPrincipe("PNEUMO Principe");
        contenuFiche2.setTechnique("PNEUMO Technique");
        contenuFiche2.setSuites("PNEUMO Suites");
        contenuFiche2.setRisquesOperation("PNEUMO Risques op");
        contenuFiche2.setSuivi("PNEUMO Suivi");

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative(ficheInformative);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche(contenuFiche);

        EdwinDatabase.getAppDatabase(this).ficheInformativeDao().insertFicheInformative(ficheInformative2);
        EdwinDatabase.getAppDatabase(this).contenuFicheDao().insertContenuFiche(contenuFiche2);
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

/*
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
                Intent intent = new Intent(MainActivity.this, FicheDetailsActivityNotUsed.class);
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
        });*/
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
        sharedPreferences = this.getSharedPreferences(YOUR_OWN_APPLICATION_PACKAGENAME_COM, Context.MODE_PRIVATE);
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

}
