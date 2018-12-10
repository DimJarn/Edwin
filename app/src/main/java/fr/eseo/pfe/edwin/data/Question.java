package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "question", foreignKeys = {@ForeignKey(entity = Quiz.class, parentColumns = "id_quiz", childColumns = "ref_quiz")},
        indices = @Index(value = "ref_quiz"))
public class Question {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id_question")
    private int idQuestion;

    @NonNull
    private String intitule;

    @NonNull
    private String choix1;

    @NonNull
    private String choix2;

    @NonNull
    public String getChoix2() {
        return choix2;
    }

    public void setChoix2(@NonNull String choix2) {
        this.choix2 = choix2;
    }

    @NonNull
    public String getChoix3() {
        return choix3;
    }

    public void setChoix3(@NonNull String choix3) {
        this.choix3 = choix3;
    }

    @NonNull
    private String choix3;


    @NonNull
    private String reponse;

    @NonNull
    @ColumnInfo(name = "ref_quiz")
    private int refQuiz;

    @Ignore
    public Question(@NonNull int idQuestion, @NonNull String intitule, @NonNull String choix1, @NonNull String choix2, @NonNull String choix3, @NonNull String reponse, @NonNull int refQuiz) {
        this.idQuestion = idQuestion;
        this.intitule = intitule;
        this.choix1 = choix1;
        this.choix2 = choix2;
        this.choix3 = choix3;
        this.reponse = reponse;
        this.refQuiz = refQuiz;
    }

    public Question(){

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
    public String getChoix1() {
        return choix1;
    }

    public void setChoix1(@NonNull String choix1) {
        this.choix1 = choix1;
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
