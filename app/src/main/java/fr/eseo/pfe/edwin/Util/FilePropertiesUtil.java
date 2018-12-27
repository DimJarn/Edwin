package fr.eseo.pfe.edwin.Util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FilePropertiesUtil {

    private static final String FICHIER_PROPERTIES = "bdd.properties";

    /**
     * MDP_BDD_DVP = getProperty(PROPERTY_PASSWORD_DVP,getContext());
     *
     * @param key
     * @param context
     * @return
     * @throws IOException
     */
    public static String getProperty(String key, Context context) throws IOException {
        Properties properties = new Properties();
        ;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(FICHIER_PROPERTIES);
        properties.load(inputStream);
        return properties.getProperty(key);
    }


}
