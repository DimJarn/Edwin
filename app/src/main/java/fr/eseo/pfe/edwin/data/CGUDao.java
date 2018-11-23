package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CGUDao {

    @Insert
    public void insertCGU(CGU cgu);

    @Update
    public void updateCGU(CGU cgu);

    @Delete
    public void deleteCGU(CGU cgu);

    @Query("SELECT * FROM cgu")
    public List<CGU> findAllCGUs();

    @Query("SELECT * FROM cgu WHERE id_cgu = :idCGU")
    public CGU findCGUFromId(int idCGU);
}
