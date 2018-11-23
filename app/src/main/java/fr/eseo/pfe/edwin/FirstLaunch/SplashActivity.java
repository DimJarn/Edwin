package fr.eseo.pfe.edwin.FirstLaunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.eseo.pfe.edwin.MainActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, fr.eseo.pfe.edwin.Main.MainActivity.class);
        startActivity(intent);
        finish();
    }
}
