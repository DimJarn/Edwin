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
public class QuestionDaoTest {

    private FicheInformativeDao ficheInformativeDao;
    private QuizDao quizDao;
    private QuestionDao questionDao;
    private EdwinDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context, EdwinDatabase.class).build();
        ficheInformativeDao = mDb.ficheInformativeDao();
        quizDao = mDb.quizDao();
        questionDao = mDb.questionDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeQuestionAndRead() throws Exception {
        FicheInformative ficheInformative = new FicheInformative();
        ficheInformative.setIdFiche(1);
        ficheInformative.setNomOperation("Opération");
        ficheInformativeDao.insertFicheInformative(ficheInformative);
        Quiz quiz = new Quiz();
        quiz.setIdQuiz(1);
        quiz.setNomQuiz("quiz");
        quiz.setRefFiche(1);
        quizDao.insertQuiz(quiz);
        Question question = new Question();
        question.setIdQuestion(1);
        question.setIntitule("intitulé");
        question.setChoix1("choix 1");
        question.setChoix2("choix 2");
        question.setChoix3("choix 3");
        question.setReponse("choix 2");
        question.setRefQuiz(1);
        questionDao.insertQuestion(question);
        Question questionInseree = questionDao.findQuestionFromId(1);
        assertEquals(question.getReponse(), questionInseree.getReponse());
    }

    @Test
    public void updateQuestionAndRead() throws Exception {
        writeQuestionAndRead();
        Question question = questionDao.findQuestionFromId(1);
        String nouvelleReponse = "choix 1";
        question.setReponse(nouvelleReponse);
        questionDao.updateQuestion(question);
        Question questionModifiee = questionDao.findQuestionFromId(1);
        assertEquals(nouvelleReponse, questionModifiee.getReponse());
    }

    @Test
    public void deleteQuestion() throws Exception {
        writeQuestionAndRead();
        Question question = questionDao.findQuestionFromId(1);
        questionDao.deleteQuestion(question);
        Question questionSupprimee = questionDao.findQuestionFromId(1);
        assertEquals(questionSupprimee, null);
    }
}
