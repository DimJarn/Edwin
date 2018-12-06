package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "apropos")
public class APropos {

    @PrimaryKey
    @NonNull
    private int id;

    @NonNull
    private String origine;

    @NonNull
    private String contact;

    public APropos(@NonNull int id, @NonNull String origine, @NonNull String contact) {
        this.id = id;
        this.origine = origine;
        this.contact = contact;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getOrigine() {
        return origine;
    }

    public void setOrigine(@NonNull String origine) {
        this.origine = origine;
    }

    @NonNull
    public String getContact() {
        return contact;
    }

    public void setContact(@NonNull String contact) {
        this.contact = contact;
    }
}
