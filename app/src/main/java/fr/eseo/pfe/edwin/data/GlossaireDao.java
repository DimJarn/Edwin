package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GlossaireDao {

    @Insert
    public void insertGlossaire(Glossaire glossaire);

    @Update
    public void updateGlossaire(Glossaire glossaire);

    @Delete
    public void deleteGlossaire(Glossaire glossaire);

    @Query("SELECT * FROM glossaire")
    public List<Glossaire> findAllGlossaires();

    @Query("SELECT * FROM glossaire WHERE id_terme = :idTerme")
    public Glossaire findGlossaireFromId(int idTerme);


    @Query("SELECT * FROM glossaire WHERE nom_terme = :nomTerme")
    public Glossaire findItemGlossaireFromName(String nomTerme);

    @Query("SELECT * FROM glossaire WHERE ref_fiche = :ref_fiche")
    public List<Glossaire> findItemGlossaireFromRefFiche(int ref_fiche);

}
