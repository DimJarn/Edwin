package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FicheInformativeDao {

    @Insert
    public void insertFicheInformative(FicheInformative ficheInformative);

    @Update
    public void updateFicheInformative(FicheInformative ficheInformative);

    @Delete
    public void deleteFicheInformative(FicheInformative ficheInformative);

    @Query("SELECT * FROM fiche_informative")
    public List<FicheInformative> findAllFichesInformatives();

    @Query("SELECT * FROM fiche_informative WHERE id_fiche = :idFiche")
    public FicheInformative findFicheInformativeFromId(int idFiche);
}
