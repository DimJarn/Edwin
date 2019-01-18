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
public class ContenuFicheDaoTest {

    private FicheInformativeDao ficheInformativeDao;
    private ContenuFicheDao contenuFicheDao;
    private EdwinDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context, EdwinDatabase.class).build();
        ficheInformativeDao = mDb.ficheInformativeDao();
        contenuFicheDao = mDb.contenuFicheDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeContenuFicheAndRead() throws Exception {
        FicheInformative ficheInformative = new FicheInformative();
        ficheInformative.setIdFiche(1);
        ficheInformative.setNomOperation("Opération");
        ficheInformativeDao.insertFicheInformative(ficheInformative);
        ContenuFiche contenuFiche = new ContenuFiche();
        contenuFiche.setIdContenuFiche(1);
        contenuFiche.setIntro("intro");
        contenuFiche.setRappelAnatomique("rappel anatomique");
        contenuFiche.setMaladie("maladie");
        contenuFiche.setRisquesMaladie("risques maladie");
        contenuFiche.setPrincipe("principe");
        contenuFiche.setTechnique("technique");
        contenuFiche.setRisquesOperation("risques opération");
        contenuFiche.setSuivi("suivi");
        contenuFiche.setSuites("suites");
        contenuFicheDao.insertContenuFiche(contenuFiche);
        ContenuFiche contenuFicheInsere = contenuFicheDao.findContenuFicheFromId(1);
        assertEquals(contenuFiche.getIntro(), contenuFicheInsere.getIntro());
    }

    @Test
    public void updateContenuFicheAndRead() throws Exception {
        writeContenuFicheAndRead();
        ContenuFiche contenuFiche = contenuFicheDao.findContenuFicheFromId(1);
        String nouvelleIntro = "new intro";
        contenuFiche.setIntro(nouvelleIntro);
        contenuFicheDao.updateContenuFiche(contenuFiche);
        ContenuFiche contenuFicheModifiee = contenuFicheDao.findContenuFicheFromId(1);
        assertEquals(nouvelleIntro, contenuFicheModifiee.getIntro());
    }

    @Test
    public void deleteFiche() throws Exception {
        writeContenuFicheAndRead();
        ContenuFiche contenuFiche = contenuFicheDao.findContenuFicheFromId(1);
        contenuFicheDao.deleteContenuFiche(contenuFiche);
        ContenuFiche contenuFicheSupprime = contenuFicheDao.findContenuFicheFromId(1);
        assertEquals(contenuFicheSupprime, null);
    }
}
