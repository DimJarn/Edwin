package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "cgu")
public class CGU {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="id_cgu")
    private int idCGU;

    @NonNull
    private String texte;

    public CGU(@NonNull int idCGU, @NonNull String texte) {
        this.idCGU = idCGU;
        this.texte = texte;
    }

    @NonNull
    public int getIdCGU() {
        return idCGU;
    }

    public void setIdCGU(@NonNull int idCGU) {
        this.idCGU = idCGU;
    }

    @NonNull
    public String getTexte() {
        return texte;
    }

    public void setTexte(@NonNull String texte) {
        this.texte = texte;
    }
}
