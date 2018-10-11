package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContenuFicheDao {

    @Insert
    public void insertContenuFiche(ContenuFiche contenuFiche);

    @Update
    public void updateContenuFiche(ContenuFiche contenuFiche);

    @Delete
    public void deleteContenuFiche(ContenuFiche contenuFiche);

    @Query("SELECT * FROM contenu_fiche")
    public List<ContenuFiche> findAllContenuFiches();

    @Query("SELECT * FROM contenu_fiche WHERE id_contenu_fiche = :idContenuFiche")
    public ContenuFiche findContenuFicheFromId(int idContenuFiche);
}
