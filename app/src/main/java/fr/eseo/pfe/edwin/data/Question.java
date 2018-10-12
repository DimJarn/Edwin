package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "question", foreignKeys = {@ForeignKey(entity= Quiz.class, parentColumns = "id_quiz", childColumns="ref_quiz")},
        indices=@Index(value="ref_quiz"))
public class Question {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_question")
    private int idQuestion;

    @NonNull
    private String intitule;

    @NonNull
    private String choix;

    @NonNull
    private String reponse;

    @NonNull
    @ColumnInfo(name="ref_quiz")
    private int refQuiz;

    public Question(@NonNull int idQuestion, @NonNull String intitule, @NonNull String choix,
                    @NonNull String reponse, @NonNull int refQuiz) {
        this.idQuestion = idQuestion;
        this.intitule = intitule;
        this.choix = choix;
        this.reponse = reponse;
        this.refQuiz = refQuiz;
    }

    @NonNull
    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(@NonNull int idQuestion) {
        this.idQuestion = idQuestion;
    }

    @NonNull
    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(@NonNull String intitule) {
        this.intitule = intitule;
    }

    @NonNull
    public String getChoix() {
        return choix;
    }

    public void setChoix(@NonNull String choix) {
        this.choix = choix;
    }

    @NonNull
    public String getReponse() {
        return reponse;
    }

    public void setReponse(@NonNull String reponse) {
        this.reponse = reponse;
    }

    @NonNull
    public int getRefQuiz() {
        return refQuiz;
    }

    public void setRefQuiz(@NonNull int refQuiz) {
        this.refQuiz = refQuiz;
    }
}
