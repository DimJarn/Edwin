package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class QuizDaoTest {

    private FicheInformativeDao ficheInformativeDao;
    private QuizDao quizDao;
    private EdwinDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context, EdwinDatabase.class).build();
        ficheInformativeDao = mDb.ficheInformativeDao();
        quizDao = mDb.quizDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeQuizAndRead() throws Exception {
        FicheInformative ficheInformative = new FicheInformative();
        ficheInformative.setIdFiche(1);
        ficheInformative.setNomOperation("Opération");
        ficheInformativeDao.insertFicheInformative(ficheInformative);
        Quiz quiz = new Quiz();
        quiz.setIdQuiz(1);
        quiz.setNomQuiz("quiz");
        quiz.setRefFiche(1);
        quizDao.insertQuiz(quiz);
        Quiz quizInsere = quizDao.findQuizFromId(1);
        assertEquals(quiz.getNomQuiz(), quizInsere.getNomQuiz());
    }

    @Test
    public void updateQuizAndRead() throws Exception {
        writeQuizAndRead();
        Quiz quiz = quizDao.findQuizFromId(1);
        String nouveauNom = "new quiz";
        quiz.setNomQuiz(nouveauNom);
        quizDao.updateQuiz(quiz);
        Quiz quizModifie = quizDao.findQuizFromId(1);
        assertEquals(nouveauNom, quizModifie.getNomQuiz());
    }

    @Test
    public void deleteQuiz() throws Exception {
        writeQuizAndRead();
        Quiz quiz = quizDao.findQuizFromId(1);
        quizDao.deleteQuiz(quiz);
        Quiz quizSupprime = quizDao.findQuizFromId(1);
        assertEquals(quizSupprime, null);
    }
}
