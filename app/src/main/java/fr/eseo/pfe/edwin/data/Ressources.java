package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "ressources")
public class Ressources {

    @PrimaryKey
    @NonNull
    private int id;

    @NonNull
    private String mail;

    public Ressources(){

    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getMail() {
        return mail;
    }

    public void setMail(@NonNull String mail) {
        this.mail = mail;
    }
}
