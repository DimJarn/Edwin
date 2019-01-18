package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RessourcesDao {

    @Insert
    public void insertRessources(Ressources ressources);

    @Update
    public void updateRessources(Ressources ressources);

    @Delete
    public void deleteRessources(Ressources ressources);

    @Query("SELECT * FROM ressources")
    public List<Ressources> findAllRessources();

    @Query("SELECT * FROM ressources WHERE id = :id")
    public Ressources findRessourcesFromId(int id);

}
