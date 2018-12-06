package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FicheInformative.class, ContenuFiche.class, Glossaire.class,
        Quiz.class, Question.class, APropos.class, CGU.class}, version = 4)
public abstract class EdwinDatabase extends RoomDatabase {

    private static EdwinDatabase instance;

    public abstract FicheInformativeDao ficheInformativeDao();
    public abstract ContenuFicheDao contenuFicheDao();
    public abstract GlossaireDao glossaireDao();
    public abstract QuizDao quizDao();
    public abstract QuestionDao questionDao();
    public abstract AProposDao aProposDao();
    public abstract CGUDao cguDao();

    public static EdwinDatabase getAppDatabase(Context context) {
        if (instance == null) {
            instance =
                    Room.databaseBuilder(context.getApplicationContext(), EdwinDatabase.class, "user-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .fallbackToDestructiveMigration().allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

}
