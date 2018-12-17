package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface JSONDao {

    @Insert
    public void insertJSON(JSON json);

    @Update
    public void updateJSON(JSON json);

    @Delete
    public void deleteJSON(JSON json);

    @Query("SELECT * FROM json")
    public List<JSON> findAllJSONs();

    @Query("SELECT * FROM json WHERE contenu = :contenu")
    public JSON findJSONFromContenu(String contenu);
}
