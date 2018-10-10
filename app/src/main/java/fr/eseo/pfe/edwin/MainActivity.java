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
/*
        //BOUTON DE REDIRECTION VERS AIDE
        Button buttonAide = (Button) findViewById(R.id.aide);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent2 = new Intent(MainActivity.this, AideActivity.class);
                startActivity(intent2);
            }
        });

        //BOUTON DE REDIRECTION VERS A PROPOS
        Button buttonAPropos = (Button) findViewById(R.id.apropos);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, AProposActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS FICHES
        Button buttonFiches = (Button) findViewById(R.id.fiches);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, FichesActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS GLOSSAIRE
        Button buttonGlossaire = (Button) findViewById(R.id.glossaire);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, GlossaireActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS MENU
        Button buttonMenu = (Button) findViewById(R.id.menu);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS QUIZ
        Button buttonQuiz = (Button) findViewById(R.id.quiz);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    */
    }
}
