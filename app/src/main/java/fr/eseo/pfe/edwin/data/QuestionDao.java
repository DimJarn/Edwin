package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface QuestionDao {

    @Insert
    public void insertQuestion(Question question);

    @Update
    public void updateQuestion(Question question);

    @Delete
    public void deleteQuestion(Question question);

    @Query("SELECT * FROM question")
    public List<Question> findAllQuestions();

    @Query("SELECT * FROM question WHERE id_question = :idQuestion")
    public Question findQuestionFromId(int idQuestion);
}
