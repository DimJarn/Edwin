package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class QuizzResultatFragment extends Fragment {
    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new QuizzResultatFragment());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_resultat_reponse, container, false);
        return view;
    }

    /**
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RatingBar bar = (RatingBar) getActivity().findViewById(R.id.ratingBar1);
        bar.setNumStars(5);
        bar.setStepSize(0.5f);
        //get text view
        TextView t = (TextView) getActivity().findViewById(R.id.textResultat);
        //get score
        Bundle bundle = this.getArguments();
        int score = bundle.getInt("score");
        //display score
        bar.setRating(score);
        switch (score) {
            case 0:
                t.setText("You scored 0%, keep learning");
                break;
            case 1:
                t.setText("You have 20%, study better");
                break;
            case 2:
                t.setText("You have 40%, keep learning");
                break;
            case 3:
                t.setText("You have 60%, good attempt");
                break;
            case 4:
                t.setText("You have 80% Hmmmm.. maybe you have been reading a lot of " +
                        "AndroidProgramming quiz");
                break;
            case 5:
                t.setText(" Whao, you have 100%, Who are you? An Android Jet brain");
                break;
        }
    }

    /**
     * creation après
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
