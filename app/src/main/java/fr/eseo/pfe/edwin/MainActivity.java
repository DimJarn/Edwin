package fr.eseo.pfe.edwin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    //tutorial slider
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //AFFICHE LA VUE ACCUEIL
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BOUTON DE REDIRECTION VERS TUTORIAL
        Button buttonTutorial = (Button) findViewById(R.id.tutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });
    }
}
