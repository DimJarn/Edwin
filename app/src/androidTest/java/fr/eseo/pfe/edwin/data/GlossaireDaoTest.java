package fr.eseo.pfe.edwin.data;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class GlossaireDaoTest {

    private GlossaireDao glossaireDao;
    private EdwinDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context, EdwinDatabase.class).build();
        glossaireDao = mDb.glossaireDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeTermeAndRead() throws Exception {
        Glossaire glossaire = new Glossaire();
        glossaire.setIdTerme(1);
        glossaire.setNomTerme("nom terme");
        glossaire.setDefinition("définition");
        glossaire.setRefFiche(1);
        glossaireDao.insertGlossaire(glossaire);
        Glossaire glossaireAjoute = glossaireDao.findGlossaireFromId(1);
        assertEquals(glossaire.getDefinition(), glossaireAjoute.getDefinition());
    }

    @Test
    public void updateTermeAndRead() throws Exception {
        writeTermeAndRead();
        Glossaire glossaire = glossaireDao.findGlossaireFromId(1);
        String nouvelleDefinition = "nouvelle définition";
        glossaire.setDefinition(nouvelleDefinition);
        glossaireDao.updateGlossaire(glossaire);
        Glossaire glossaireModifie = glossaireDao.findGlossaireFromId(1);
        assertEquals(nouvelleDefinition, glossaireModifie.getDefinition());
    }

    @Test
    public void deleteTerme() throws Exception {
        writeTermeAndRead();
        Glossaire glossaire = glossaireDao.findGlossaireFromId(1);
        glossaireDao.deleteGlossaire(glossaire);
        Glossaire glossaireSupprime = glossaireDao.findGlossaireFromId(1);
        assertEquals(glossaireSupprime, null);
    }
}
