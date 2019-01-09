package fr.eseo.pfe.edwin.launch;

import android.app.Application;
import android.os.SystemClock;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(4000); //temps d'attente de 4s
    }
}
