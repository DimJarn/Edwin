package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "glossaire", foreignKeys = {@ForeignKey
        (entity= FicheInformative.class, parentColumns = "id_fiche", childColumns="ref_fiche")},
        indices=@Index(value="ref_fiche"))
public class Glossaire {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_terme")
    private int idTerme;

    @NonNull
    @ColumnInfo(name="nom_terme")
    private String nomTerme;

    @NonNull
    @ColumnInfo(name="definition")
    private String definition;

    private String image;

    @ColumnInfo(name="ref_fiche")
    private int refFiche;

    public Glossaire(){
    }

    public Glossaire(@NonNull int idTerme, @NonNull String nomTerme, @NonNull String definition, String image, int refFiche){
        this.idTerme = idTerme;
        this.nomTerme = nomTerme;
        this.definition = definition;
        this.image = image;
        this.refFiche = refFiche;
    }

    @NonNull
    public int getIdTerme() {
        return idTerme;
    }

    public void setIdTerme(@NonNull int idTerme) {
        this.idTerme = idTerme;
    }

    @NonNull
    public String getNomTerme() {
        return nomTerme;
    }

    public void setNomTerme(@NonNull String nomTerme) {
        this.nomTerme = nomTerme;
    }

    @NonNull
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(@NonNull String definition) {
        this.definition = definition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRefFiche() {
        return refFiche;
    }

    public void setRefFiche(int refFiche) {
        this.refFiche = refFiche;
    }
}
