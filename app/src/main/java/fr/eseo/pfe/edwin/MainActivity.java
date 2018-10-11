package fr.eseo.pfe.edwin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    //tutorial slider
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Button buttonTutorial = null;
    private Button buttonAide = null;

    // Declare Prefs as global in MainActivity so you can access it from it's other methods
    private SharedPreferences Prefs;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        //AFFICHE LA VUE ACCUEIL
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detectFirstUseAndOpenDialogForTutorial();

        //BOUTON DE REDIRECTION VERS TUTORIAL
        buttonTutorial = findViewById(R.id.tutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View actualView) {
                Intent intent = new Intent(getBaseContext(), TutorialActivity.class);
                startActivityForResult(intent,0);
            }
        });

        //BOUTON DE REDIRECTION VERS AIDE
        buttonAide = findViewById(R.id.aide);
        buttonAide.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(getBaseContext(), AideActivity.class);
                startActivityForResult(intent,0);
            }
        });


        //BOUTON DE REDIRECTION VERS A PROPOS
        Button buttonAPropos = (Button) findViewById(R.id.apropos);
        buttonAPropos.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, AProposActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS FICHES
        Button buttonFiches = (Button) findViewById(R.id.fiches);
        buttonFiches.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, FichesActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS GLOSSAIRE
        Button buttonGlossaire = (Button) findViewById(R.id.glossaire);
        buttonGlossaire.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, GlossaireActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS MENU
        Button buttonMenu = (Button) findViewById(R.id.menu);
        buttonMenu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS QUIZ
        Button buttonQuiz = (Button) findViewById(R.id.quiz);
        buttonQuiz.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    private void detectFirstUseAndOpenDialogForTutorial() {
        Prefs = this.getSharedPreferences("YOUR.OWN.APPLICATION.PACKAGENAME.COM", Context.MODE_PRIVATE);


        if (promptTutorial()) {
            // Tutorial was never prompted
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            // Build prompt dialog
            builder.setTitle("Bienvenue sur l'application Edwin !")
                    .setMessage("Souhaitez-vous voir un court tutoriel sur le fonctionnement de l'application ?")
                    .setPositiveButton(R.string.tutorial_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do something if user clicked YES
                            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.tutorial_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();


        }
    }


    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notice");
        builder.setMessage("Launching this missile will destroy the entire universe. Is this what you intended to do?");

        // add the buttons
        builder.setPositiveButton("Launch missile", null);
        builder.setNeutralButton("Remind me later", null);
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
        String keyTutorial = Prefs.getString("keyTutorial", "NullTutorial");
        // Log what we found in shared preference
        Log.d(TAG, "Shared Pref read: [keyTutorial: " + keyTutorial + "]");

        if (keyTutorial.contains("NullTutorial")) {
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


}
