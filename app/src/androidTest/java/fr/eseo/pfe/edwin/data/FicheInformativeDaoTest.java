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
public class FicheInformativeDaoTest {

    private FicheInformativeDao ficheInformativeDao;
    private EdwinDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context, EdwinDatabase.class).build();
        ficheInformativeDao = mDb.ficheInformativeDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeFicheAndRead() throws Exception {
        FicheInformative ficheInformative = new FicheInformative();
        ficheInformative.setIdFiche(1);
        ficheInformative.setNomOperation("Opération");
        ficheInformativeDao.insertFicheInformative(ficheInformative);
        FicheInformative byId = ficheInformativeDao.findFicheInformativeFromId(1);
        assertEquals(byId.getNomOperation(), ficheInformative.getNomOperation());
    }

    @Test
    public void updateFicheAndRead() throws Exception {
        writeFicheAndRead();
        FicheInformative ficheInformative = ficheInformativeDao.findFicheInformativeFromId(1);
        String nomOperationInitial = ficheInformative.getNomOperation();
        String nouveauNomOperation = "newOpération";
        ficheInformative.setNomOperation(nouveauNomOperation);
        ficheInformativeDao.updateFicheInformative(ficheInformative);
        FicheInformative ficheInformativeModifiee = ficheInformativeDao.findFicheInformativeFromId(1);
        assertEquals(nouveauNomOperation, ficheInformativeModifiee.getNomOperation());
    }

    @Test
    public void deleteFiche() throws Exception {
        writeFicheAndRead();
        FicheInformative ficheInformative = ficheInformativeDao.findFicheInformativeFromId(1);
        ficheInformativeDao.deleteFicheInformative(ficheInformative);
        FicheInformative ficheInformativeSupprimee = ficheInformativeDao.findFicheInformativeFromId(1);
        assertEquals(ficheInformativeSupprimee, null);
    }
}
