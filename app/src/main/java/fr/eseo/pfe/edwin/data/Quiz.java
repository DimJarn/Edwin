package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "quiz", foreignKeys = {@ForeignKey(entity= FicheInformative.class, parentColumns = "id_fiche", childColumns="ref_fiche")})
public class Quiz {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_quiz")
    private int idQuiz;

    @NonNull
    @ColumnInfo(name="nom_quiz")
    private String nomQuiz;

    @NonNull
    @ColumnInfo(name="ref_fiche")
    private int refFiche;

    public Quiz(@NonNull int idQuiz, @NonNull String nomQuiz, @NonNull int refFiche){
        this.idQuiz = idQuiz;
        this.nomQuiz = nomQuiz;
        this.refFiche = refFiche;
    }

    @NonNull
    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(@NonNull int idQuiz) {
        this.idQuiz = idQuiz;
    }

    @NonNull
    public String getNomQuiz() {
        return nomQuiz;
    }

    public void setNomQuiz(@NonNull String nomQuiz) {
        this.nomQuiz = nomQuiz;
    }

    @NonNull
    public int getRefFiche() {
        return refFiche;
    }

    public void setRefFiche(@NonNull int refFiche) {
        this.refFiche = refFiche;
    }
}
