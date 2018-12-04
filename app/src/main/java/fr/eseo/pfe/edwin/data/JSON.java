package fr.eseo.pfe.edwin.data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import java.net.HttpURLConnection;

public class JSON {

    public static ArrayList<FicheInformative> getFiches(){

        ArrayList<FicheInformative> fichesList = new ArrayList<FicheInformative>();

        try {
            String myurl= "http://effi-qua-propre-services.fr/fiche.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            String result = new RetrieveFeedTask().execute(connection).get();

            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(result);
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject objFiche = new JSONObject(array.getString(i));

                System.out.println(objFiche);

                FicheInformative ficheInformative = new FicheInformative();

                ficheInformative.setIdFiche(objFiche.getInt("idFiche"));
                ficheInformative.setNomOperation(objFiche.getString("nomOperation"));

                fichesList.add(ficheInformative);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fichesList;
    }

    public static ArrayList<ContenuFiche> getContenuFiches(){

        ArrayList<ContenuFiche> contenuFicheArrayList = new ArrayList<ContenuFiche>();

        try {
            String myurl= "http://effi-qua-propre-services.fr/contenufiche.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            String result = new RetrieveFeedTask().execute(connection).get();

            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(result);
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject objContenuFiche = new JSONObject(array.getString(i));

                System.out.println(objContenuFiche);

                ContenuFiche contenuFiche = new ContenuFiche();

                contenuFiche.setIdContenuFiche(objContenuFiche.getInt("idContenuFiche"));
                contenuFiche.setIntro(objContenuFiche.getString("intro"));
                contenuFiche.setRappelAnatomique(objContenuFiche.getString("rappelAnatomique"));
                contenuFiche.setMaladie(objContenuFiche.getString("maladie"));
                contenuFiche.setRisquesMaladie(objContenuFiche.getString("risquesMaladie"));
                contenuFiche.setPrincipe(objContenuFiche.getString("principe"));
                contenuFiche.setTechnique(objContenuFiche.getString("technique"));
                contenuFiche.setSuites(objContenuFiche.getString("suites"));
                contenuFiche.setRisquesOperation(objContenuFiche.getString("risquesOperation"));
                contenuFiche.setSuivi(objContenuFiche.getString("suivi"));

                contenuFicheArrayList.add(contenuFiche);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contenuFicheArrayList;
    }

    public static ArrayList<Glossaire> getGlossaire(){

        ArrayList<Glossaire> glossaire = new ArrayList<Glossaire>();

        try {
            String myurl= "http://effi-qua-propre-services.fr/glossaire.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            String result = new RetrieveFeedTask().execute(connection).get();

            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(result);
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject objGlossaire = new JSONObject(array.getString(i));

                System.out.println(objGlossaire);

                Glossaire terme = new Glossaire();

                terme.setIdTerme(objGlossaire.getInt("idTerme"));
                terme.setNomTerme(objGlossaire.getString("nomTerme"));
                terme.setDefinition(objGlossaire.getString("definition"));
                terme.setRefFiche(objGlossaire.getInt("refFiche"));

                glossaire.add(terme);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return glossaire;
    }

    public static class RetrieveFeedTask extends AsyncTask<HttpURLConnection, Void, String> {

        private Exception exception;

        protected String doInBackground(HttpURLConnection... connections) {
            try {


                InputStream inputStream = connections[0].getInputStream();

                String result = InputStreamOperations.InputStreamToString(inputStream);

                return result;
            } catch (Exception e) {
                this.exception = e;
                System.out.println("non");
                return null;
            }
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }

    }

}
