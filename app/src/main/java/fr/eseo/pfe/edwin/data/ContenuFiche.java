package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "contenu_fiche", foreignKeys = {@ForeignKey(entity= FicheInformative.class, parentColumns = "id_fiche", childColumns="id_contenu_fiche")})
public class ContenuFiche {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_contenu_fiche")
    private int idContenuFiche;

    @NonNull
    private String maladie;

    @NonNull
    @ColumnInfo(name="risques_maladie")
    private String risquesMaladie;

    @NonNull
    private String principe;

    @NonNull
    private String technique;

    @NonNull
    private String suites;

    @NonNull
    @ColumnInfo(name="risques_intervention")
    private String risquesOperation;

    @NonNull
    private String suivi;

    public ContenuFiche(){

    }

    public ContenuFiche(@NonNull int idContenuFiche, @NonNull String maladie, @NonNull String risquesMaladie,
                        @NonNull String principe, @NonNull String technique, @NonNull String suites,
                        @NonNull String risquesOperation, @NonNull String suivi){
        this.idContenuFiche = idContenuFiche;
        this.maladie = maladie;
        this.risquesMaladie = risquesMaladie;
        this.principe = principe;
        this.technique = technique;
        this.suites = suites;
        this.risquesOperation = risquesOperation;
        this.suivi = suivi;
    }

    @NonNull
    public int getIdContenuFiche() {
        return idContenuFiche;
    }

    public void setIdContenuFiche(@NonNull int idContenuFiche) {
        this.idContenuFiche = idContenuFiche;
    }

    @NonNull
    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(@NonNull String maladie) {
        this.maladie = maladie;
    }

    @NonNull
    public String getRisquesMaladie() {
        return risquesMaladie;
    }

    public void setRisquesMaladie(@NonNull String risquesMaladie) {
        this.risquesMaladie = risquesMaladie;
    }

    @NonNull
    public String getPrincipe() {
        return principe;
    }

    public void setPrincipe(@NonNull String principe) {
        this.principe = principe;
    }

    @NonNull
    public String getTechnique() {
        return technique;
    }

    public void setTechnique(@NonNull String technique) {
        this.technique = technique;
    }

    @NonNull
    public String getSuites() {
        return suites;
    }

    public void setSuites(@NonNull String suites) {
        this.suites = suites;
    }

    @NonNull
    public String getRisquesOperation() {
        return risquesOperation;
    }

    public void setRisquesOperation(@NonNull String risquesOperation) {
        this.risquesOperation = risquesOperation;
    }

    @NonNull
    public String getSuivi() {
        return suivi;
    }

    public void setSuivi(@NonNull String suivi) {
        this.suivi = suivi;
    }
}
