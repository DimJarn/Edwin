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
    private Button buttonTutorial = null;
    private Button buttonAide = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        //AFFICHE LA VUE ACCUEIL
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BOUTON DE REDIRECTION VERS TUTORIAL
        buttonTutorial = findViewById(R.id.tutorial);
        buttonTutorial.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actualView)
            {
                Intent intent = new Intent(getBaseContext(), TutorialActivity.class);
                startActivityForResult(intent,0);
            }
        });

        //BOUTON DE REDIRECTION VERS AIDE
        buttonAide = findViewById(R.id.aide);
        buttonAide.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(getBaseContext(), AideActivity.class);
                startActivityForResult(intent,0);
            }
        });


        //BOUTON DE REDIRECTION VERS A PROPOS
        Button buttonAPropos = (Button) findViewById(R.id.apropos);
        buttonAPropos.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, AProposActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS FICHES
        Button buttonFiches = (Button) findViewById(R.id.fiches);
        buttonFiches.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, FichesActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS GLOSSAIRE
        Button buttonGlossaire = (Button) findViewById(R.id.glossaire);
        buttonGlossaire.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, GlossaireActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS MENU
        Button buttonMenu = (Button) findViewById(R.id.menu);
        buttonMenu.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        //BOUTON DE REDIRECTION VERS QUIZ
        Button buttonQuiz = (Button) findViewById(R.id.quiz);
        buttonQuiz.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View actuelView)
            {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}
