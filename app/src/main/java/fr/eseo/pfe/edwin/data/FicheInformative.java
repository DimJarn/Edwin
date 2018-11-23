package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "fiche_informative")
public class FicheInformative implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_fiche")
    private int idFiche;

    @NonNull
    @ColumnInfo(name="nom_operation")
    private String nomOperation;

    @ColumnInfo(name="schema_operation")
    private String schemaOperation;

    @NonNull
    @ColumnInfo(name="ref_contenu_fiche")
    private int refContenuFiche;

    public FicheInformative(){

    }

    public FicheInformative(@NonNull int idFiche, @NonNull String nomOperation, String schemaOperation, @NonNull int refContenuFiche){
        this.idFiche = idFiche;
        this.nomOperation = nomOperation;
        this.schemaOperation = schemaOperation;
        this.refContenuFiche = refContenuFiche;
    }

    @NonNull
    public int getIdFiche() {
        return idFiche;
    }

    public void setIdFiche(@NonNull int idFiche) {
        this.idFiche = idFiche;
    }

    @NonNull
    public String getNomOperation() {
        return nomOperation;
    }

    public void setNomOperation(@NonNull String nomOperation) {
        this.nomOperation = nomOperation;
    }

    public String getSchemaOperation() {
        return schemaOperation;
    }

    public void setSchemaOperation(String schemaOperation) {
        this.schemaOperation = schemaOperation;
    }

    @NonNull
    public int getRefContenuFiche() {
        return refContenuFiche;
    }

    public void setRefContenuFiche(@NonNull int refContenuFiche) {
        this.refContenuFiche = refContenuFiche;
    }
}
