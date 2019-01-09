package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Fragment Resultat Quiz, extends de Fragment
 * Affichage de la page Resultat Quiz et intégration du menu
 */
public class QuizzResultatFragment extends Fragment {

    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new QuizzResultatFragment());
    }

    /**
     * @param inflater           l'inflater
     * @param container          le container
     * @param savedInstanceState l'instance
     * @return la vue
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.quiz_resultat_reponse, container, false);
    }

    /**
     * @param view               la vue
     * @param savedInstanceState l'instance
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get text view
        TextView t = getActivity().findViewById(R.id.textResultat);
        //get text view
        TextView ts = getActivity().findViewById(R.id.score);
        //get image view
        ImageView i = getActivity().findViewById(R.id.imageResult);
        //get score
        Bundle bundle = this.getArguments();
        int score = bundle.getInt("score");
        //display image result
        switch (score) {
            case 0:
                i.setImageResource(R.drawable.quiz0);
                break;
            case 1:
                i.setImageResource(R.drawable.quiz1);
                break;
            case 2:
                i.setImageResource(R.drawable.quiz2);
                break;
            case 3:
                i.setImageResource(R.drawable.quiz3);
                break;
            case 4:
                i.setImageResource(R.drawable.quiz4);
                break;
            case 5:
                i.setImageResource(R.drawable.quiz5);
                break;
            default:
                t.setText("Oups");
        }
        //display text score
        switch (score) {
            case 0:
                t.setText("Avez-vous répondu au hasard ?! Allez on s'y remet");
                break;
            case 1:
                t.setText("1 point c'est mieux que rien...Retentez votre chance");
                break;
            case 2:
                t.setText("Il faut encore réviser... Je crois en vous");
                break;
            case 3:
                t.setText("Au dessus de la moyenne. Vous pouvez faire mieux je le sais");
                break;
            case 4:
                t.setText("Bravo, vous vous rapprochez de la perfection !");
                break;
            case 5:
                t.setText("Félicitations ! Brillant ! Vous êtes prets");
                break;
            default:
                t.setText("Oups");
        }

        //display score
        switch (score) {
            case 0:
                ts.setText("0/5");
                break;
            case 1:
                ts.setText("1/5");
                break;
            case 2:
                ts.setText("2/5");
                break;
            case 3:
                ts.setText("3/5");
                break;
            case 4:
                ts.setText("4/5");
                break;
            case 5:
                ts.setText("5/5");
                break;
            default:
                ts.setText("Oups");
        }

        Button buttonHome = view.findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AccueilActivity.class);
                startActivity(i);
            }
        });

        Button buttonQuiz = view.findViewById(R.id.buttonAgain);
        buttonQuiz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QuizzActivity.class);
                startActivity(i);
            }
        });
    }
}
