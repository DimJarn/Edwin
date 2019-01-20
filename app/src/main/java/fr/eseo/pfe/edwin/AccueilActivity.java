package fr.eseo.pfe.edwin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import fr.eseo.pfe.edwin.launch.TutorialActivity;
import fr.eseo.pfe.edwin.main.MainActivity;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);
        termsAndConditionsDialog();/**
         * Launch the box for the tearms and conditions
         * After, launch the Tutorial for the first time
         */
        setupToolbar();
        setUpButton();

        toolbar = findViewById(R.id.toolbar_real);
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

    }

    /**
     * Methode privée pour initialiser le drawer toogle
     *
     * @return the action bar
     */
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

    /**
     * Methode qui va creer et importer le toolbar pour naviguer vers le menu
     */
    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Accueil");

    }

    /**
     * Methode pour initialiser les boutons de la page d'accueil
     */
    private void setUpButton() {
        Button buttonHelp = findViewById(R.id.buttonNeedHelp);
        buttonHelp.setOnClickListener(this);

        Button buttonYourOperation = findViewById(R.id.buttonYourOperation);
        buttonYourOperation.setOnClickListener(this);

        Button buttonMoreInfo = findViewById(R.id.buttonMoreInfo);
        buttonMoreInfo.setOnClickListener(this);
    }

    /**
     * Methode pour attribuer les actions aux boutons
     *
     * @param v, la vue
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
     * Methode qui attribue une action en fonction de l'option selectionnée
     *
     * @param item l'item du menu selectionné
     * @return item l'item seletionné
     */
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    /**
     * Methode onBack
     * Si l'utilisateur clique sur retour, fermeture de l'application
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(intent);
    }

    /**
     * Pop up to read the tearms and conditions
     * If not accepted, the pop up will be at every start of the app
     */
    private void termsAndConditionsDialog() {
        detectFirstUseAOpenTutorialAndBox();

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
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    });


            AlertDialog alertDialog = builder.create();
            //alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            alertDialog.getWindow().setGravity(Gravity.TOP);
            alertDialog.show();

        }
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

            Intent intent = new Intent(AccueilActivity.this, TutorialActivity.class);
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
}
