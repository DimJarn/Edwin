package fr.eseo.pfe.edwin;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import fr.eseo.pfe.edwin.data.EdwinDatabase;

/**
 * Fragment Parametres, extends de Fragemnt
 * Affichage de la page Parametres et intégration du menu
 *
 * @author dimitrijarneau
 */
public class ParametresFragment extends Fragment implements View.OnClickListener {

    private static final String PROPERTY_PASSWORD_DVP = "password_dev";
    private static String MDP_BDD_DVP = null;
    FragmentActivity listener;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    private String m_Text = "";

    public static ParametresFragment newInstance() {
        return (new ParametresFragment());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }

    /**
     * Création de la vue
     *
     * @param inflater           l'inflater
     * @param container          le container
     * @param savedInstanceState l'instance
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.parametres_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpButton();

        TextView textViewAppVersion = view.findViewById(R.id.app_version_content);
        textViewAppVersion.setText(versionCode + " " + versionName);

        TextView textView = view.findViewById(R.id.device_id_content);
        textView.setText(Settings.Secure.getString(view.getContext()
                        .getContentResolver(),
                Settings.Secure.ANDROID_ID));

        //String[] test = chargerProprietes();

    }


    /**
     * Methode pour initialiser les boutons de la page d'accueil
     */
    private void setUpButton() {
        Button buttonDvpMode = Objects.requireNonNull(getView()).findViewById(R.id
                .idBtnModeDeveloppeur);
        buttonDvpMode.setOnClickListener(this);


        Button buttonEvaluerApp = getView().findViewById(R.id
                .idBtnevaluerApplicationContent);
        buttonEvaluerApp.setOnClickListener(this);

        Button buttonEnvoyerMail = getView().findViewById(R.id
                .idcontacterDvpeurContent);
        buttonEnvoyerMail.setOnClickListener(this);


        Button buttonPartagerByMail = getView().findViewById(R.id
                .idPartagerAppliContent);
        buttonPartagerByMail.setOnClickListener(this);
    }

    /**
     * Methode pour attribuer les actions aux boutons
     *
     * @param v la vue
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idBtnModeDeveloppeur:
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Mode developpeur");

                builder.setMessage("Mot de passe : ");
                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a
                // password, and will
                // mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                        .TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();

                        try {
                            MDP_BDD_DVP = getProperty(PROPERTY_PASSWORD_DVP, getContext());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (m_Text.equals(MDP_BDD_DVP)) {
                            Toasty.success(getContext(), "Mot de passe correct !", Toast
                                    .LENGTH_SHORT, true)
                                    .show();

                            Fragment fragment = ParametresDetailsFragment.newInstance();
                            // Très important !!
                            //la méthode .addToBackStack permet d'utiliser le bouton retour dans
                            // le fragment
                            getFragmentManager().beginTransaction().addToBackStack(null).replace
                                    (R.id
                                            .main_content_parametres_fragment, fragment).commit();

                        } else {
                            Toasty.error(getContext(), "Mot de passe incorrect !", Toast
                                    .LENGTH_SHORT, true)
                                    .show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                break;*/
                try {

                    Intent viewIntent =
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://docs.google" +
                                            ".com/forms/d/e/1FAIpQLSeVbUVImTys0g2srYdl5bjA6AGamqyR5X4kGFH915qUDcyUWA/viewform"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toasty.error(getContext(), "Impossible de se connecter, Essayer de " +
                                    "nouveau..." + e,
                            Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.idBtnevaluerApplicationContent:
                try {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse("https://play.google.com/store/apps/details?id=com" +
                                            ".adeebhat.rabbitsvilla"));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    Toasty.error(getContext(), "Impossible de se connecter, Essayer de " +
                                    "nouveau..." + e,
                            Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.idcontacterDvpeurContent:
                try {
                    String adresseMail = EdwinDatabase.getAppDatabase(getContext())
                            .ressourcesDao().findRessourcesFromId(1).getMail();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {adresseMail};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback requête");
                    String deviceBrand = android.os.Build.MANUFACTURER;
                    String deviceModel = android.os.Build.MODEL;
                    String osVersion = android.os.Build.VERSION.RELEASE;
                    intent.putExtra(Intent.EXTRA_TEXT, "ID du téléphone : " + Settings.Secure
                            .getString(Objects.requireNonNull(getView()).getContext()
                                            .getContentResolver(),
                                    Settings.Secure.ANDROID_ID) + "\n Version de l'application : " +
                            versionCode + " " + versionName + "\n Téléphone : " + deviceBrand +
                            "" + deviceModel + "\n Version de l'OS : " + osVersion);
                    intent.putExtra(Intent.EXTRA_CC, adresseMail);
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Envoyer email"));
                } catch (Exception e) {
                    Toasty.error(getContext(), "Impossible d'envoyer l'email, Essayer de " +
                            "nouveau..." + e, Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.idPartagerAppliContent:
                try {
                    String adresseMail = EdwinDatabase.getAppDatabase(getContext())
                            .ressourcesDao().findRessourcesFromId(1).getMail();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    String[] recipients = {};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Edwin");
                    String lienVersGooglePlay = "";

                    intent.putExtra(Intent.EXTRA_TEXT, "Essaie l'application gratuite 'Edwin' " +
                            "!" + "\n " + lienVersGooglePlay);
                    intent.putExtra(Intent.EXTRA_CC, adresseMail);
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Envoyer email"));
                } catch (Exception e) {
                    Toasty.error(getContext(), "Impossible d'envoyer l'email, Essayer de " +
                            "nouveau..." + e, Toast.LENGTH_SHORT, true).show();
                }
                break;
            default:
                Log.d("AccueilActivityMessage", "Default value");
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

}
