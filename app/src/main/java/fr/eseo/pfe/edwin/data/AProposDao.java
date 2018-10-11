package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AProposDao {

    @Insert
    public void insertAPropos(APropos aPropos);

    @Update
    public void updateAPropos(APropos aPropos);

    @Delete
    public void deleteAPropos(APropos aPropos);

    @Query("SELECT * FROM apropos")
    public List<APropos> findAPropos();

    @Query("SELECT * FROM apropos WHERE id = :id")
    public APropos findAProposFromId(int id);
}
