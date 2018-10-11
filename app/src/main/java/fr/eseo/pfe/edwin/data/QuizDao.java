package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface QuizDao {

    @Insert
    public void insertQuiz(Quiz quiz);

    @Update
    public void updateQuiz(Quiz quiz);

    @Delete
    public void deleteQuiz(Quiz quiz);

    @Query("SELECT * FROM quiz")
    public List<Quiz> findAllQuiz();

    @Query("SELECT * FROM quiz WHERE id_quiz = :idQuiz")
    public Quiz findQuizFromId(int idQuiz);
}
