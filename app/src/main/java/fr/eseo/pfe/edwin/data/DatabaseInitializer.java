package fr.eseo.pfe.edwin.data;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final EdwinDatabase db, ArrayList ficheInformativeArrayList,
                                     ArrayList contenuFicheArrayList, ArrayList glossaireArrayList,
                                     ArrayList quizArrayList, ArrayList questionArrayList, ArrayList ressourcesArraylist) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute(ficheInformativeArrayList, contenuFicheArrayList, glossaireArrayList, quizArrayList, questionArrayList, ressourcesArraylist);
    }

    public static void populateAsyncJSON(@NonNull final EdwinDatabase db, ArrayList jsonArrayList) {
        PopulateJSONAsync task = new PopulateJSONAsync(db);
        task.execute(jsonArrayList);
    }


    private static FicheInformative addFicheInformative(final EdwinDatabase db, FicheInformative ficheInformative) {
        db.ficheInformativeDao().insertFicheInformative(ficheInformative);
        return ficheInformative;
    }

    private static ContenuFiche addContenuFiche(final EdwinDatabase db, ContenuFiche contenuFiche){
        db.contenuFicheDao().insertContenuFiche(contenuFiche);
        return contenuFiche;
    }

    private static CGU addCGU(final EdwinDatabase db, CGU cgu){
        db.cguDao().insertCGU(cgu);
        return cgu;
    }

    private static APropos addAPropos(final EdwinDatabase db, APropos aPropos){
        db.aProposDao().insertAPropos(aPropos);
        return aPropos;
    }

    private static Glossaire addGlossaire(final EdwinDatabase db, Glossaire glossaire){
        db.glossaireDao().insertGlossaire(glossaire);
        return glossaire;
    }

    private static Question addQuestion(final EdwinDatabase db, Question question){
        db.questionDao().insertQuestion(question);
        return question;
    }

    private static Quiz addQuiz(final EdwinDatabase db, Quiz quiz){
        db.quizDao().insertQuiz(quiz);
        return quiz;
    }

    private static JSON addJSON(final EdwinDatabase db, JSON json){
        db.jsonDao().insertJSON(json);
        return json;
    }

    private static Ressources addRessources(final EdwinDatabase db, Ressources ressources){
        db.ressourcesDao().insertRessources(ressources);
        return ressources;
    }

    private static void populateDatabase(EdwinDatabase db, ArrayList<FicheInformative> ficheInformativeArrayList,
                                         ArrayList<ContenuFiche> contenuFicheArrayList, ArrayList<Glossaire> glossaireArrayList,
                                         ArrayList<Quiz> quizArrayList, ArrayList<Question> questionArrayList, ArrayList<Ressources> ressourcesArrayList) {
        for(FicheInformative ficheInformative : ficheInformativeArrayList){
            addFicheInformative(db, ficheInformative);
        }

        for(ContenuFiche contenuFiche : contenuFicheArrayList){
            addContenuFiche(db, contenuFiche);
        }

        for(Glossaire glossaire : glossaireArrayList){
            addGlossaire(db, glossaire);
        }

        for(Quiz quiz : quizArrayList){
            addQuiz(db, quiz);
        }

        for(Question question : questionArrayList){
            addQuestion(db, question);
        }

        for(Ressources ressources : ressourcesArrayList){
            addRessources(db, ressources);
        }
    }

    private static void populateJSON(EdwinDatabase db, ArrayList<JSON> jsonArrayList) {
        for(JSON json : jsonArrayList){
            addJSON(db, json);
        }
    }

    private static class PopulateDbAsync extends AsyncTask<ArrayList, Void, Void> {

        private final EdwinDatabase mDb;

        PopulateDbAsync(EdwinDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final ArrayList... params) {
            populateDatabase(mDb, params[0], params[1], params[2], params[3], params[4], params[5]);
            return null;
        }
    }

    private static class PopulateJSONAsync extends AsyncTask<ArrayList, Void, Void> {

        private final EdwinDatabase mDb;

        PopulateJSONAsync(EdwinDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final ArrayList... params) {
            populateJSON(mDb, params[0]);
            return null;
        }
    }
}
