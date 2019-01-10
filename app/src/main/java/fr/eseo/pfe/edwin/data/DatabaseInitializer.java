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
                                     ArrayList quizArrayList, ArrayList questionArrayList) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute(ficheInformativeArrayList, contenuFicheArrayList, glossaireArrayList, quizArrayList, questionArrayList);
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

    private static void populateDatabase(EdwinDatabase db, ArrayList<FicheInformative> ficheInformativeArrayList,
                                         ArrayList<ContenuFiche> contenuFicheArrayList, ArrayList<Glossaire> glossaireArrayList,
                                         ArrayList<Quiz> quizArrayList, ArrayList<Question> questionArrayList) {
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
    }

    private static class PopulateDbAsync extends AsyncTask<ArrayList, Void, Void> {

        private final EdwinDatabase mDb;

        PopulateDbAsync(EdwinDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final ArrayList... params) {
            populateDatabase(mDb, params[0], params[1], params[2], params[3], params[4]);
            return null;
        }
    }
}
