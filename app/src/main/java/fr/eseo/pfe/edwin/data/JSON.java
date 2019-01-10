package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@Entity(tableName = "json")
public class JSON {

    @PrimaryKey
    @NonNull
    private int id;

    private String json;

    private String contenu;

    public JSON(){

    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getJson() {
        return json;
    }

    public String getContenu() {
        return contenu;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public static ArrayList<FicheInformative> getFiches(){

        ArrayList<FicheInformative> fichesList = new ArrayList<FicheInformative>();

        try {
            String myurl = "http://simonchirat.com/ficheJSON.php";
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

    public static String getJSONFiches() {

        String result = "";

        try {
            String myurl = "http://simonchirat.com/ficheJSON.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            result = new RetrieveFeedTask().execute(connection).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<ContenuFiche> getContenuFiches(){

        ArrayList<ContenuFiche> contenuFicheArrayList = new ArrayList<ContenuFiche>();

        try {
            String myurl = "http://simonchirat.com/contenufiche.php";
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

    public static String getJSONContenuFiche(){

        String result = "";

        try {
            String myurl = "http://simonchirat.com/contenufiche.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            result = new RetrieveFeedTask().execute(connection).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Glossaire> getGlossaire(){

        ArrayList<Glossaire> glossaire = new ArrayList<Glossaire>();

        try {
            String myurl= "http://simonchirat.com/glossaireJSON.php";
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

    public static String getJSONGlossaire(){

        String result = "";

        try {
            String myurl = "http://simonchirat.com/glossaireJSON.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            result = new RetrieveFeedTask().execute(connection).get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Quiz> getQuiz(){

        ArrayList<Quiz> quizArrayList = new ArrayList<Quiz>();

        try {
            String myurl = "http://simonchirat.com/quizJSON.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            String result = new RetrieveFeedTask().execute(connection).get();

            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(result);
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject objQuiz = new JSONObject(array.getString(i));

                Quiz quiz = new Quiz();

                quiz.setIdQuiz(objQuiz.getInt("idQuiz"));
                quiz.setNomQuiz(objQuiz.getString("nomQuiz"));
                quiz.setRefFiche(objQuiz.getInt("refFiche"));

                quizArrayList.add(quiz);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quizArrayList;
    }

    public static String getJSONQuiz(){

        String result = "";

        try {
            String myurl = "http://simonchirat.com/quizJSON.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            result = new RetrieveFeedTask().execute(connection).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<Question> getQuestions(){

        ArrayList<Question> questions = new ArrayList<Question>();

        try {
            String myurl = "http://simonchirat.com/questions.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            String result = new RetrieveFeedTask().execute(connection).get();

            // On récupère le tableau d'objets qui nous concernent
            JSONArray array = new JSONArray(result);
            // Pour tous les objets on récupère les infos
            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject objQuestion = new JSONObject(array.getString(i));

                Question question = new Question();

               question.setIdQuestion(objQuestion.getInt("idQuestion"));
               question.setIntitule(objQuestion.getString("intitule"));
               question.setChoix1(objQuestion.getString("choix1"));
               question.setChoix2(objQuestion.getString("choix2"));
               question.setChoix3(objQuestion.getString("choix3"));
               question.setReponse(objQuestion.getString("reponse"));
               question.setRefQuiz(objQuestion.getInt("refQuiz"));

                questions.add(question);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static String getJSONQuestions(){

        String result = "";

        try {
            String myurl = "http://simonchirat.com/questions.php";
            URL url = new URL(myurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            result = new RetrieveFeedTask().execute(connection).get();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static class RetrieveFeedTask extends AsyncTask<HttpURLConnection, Void, String> {

        private Exception exception;

        protected String doInBackground(HttpURLConnection... connections) {
            try {


                InputStream inputStream = connections[0].getInputStream();

                String result = InputStreamOperations.inputStreamToString(inputStream);

                return result;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }

    }

}
