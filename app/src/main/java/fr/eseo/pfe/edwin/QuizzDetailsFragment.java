package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.Question;

public class QuizzDetailsFragment extends Fragment {

    int questionId = 0;
    int score = 0;
    Question currentQuestion;
    TextView textViewQuiz;
    RadioButton radioButton1, radioButton2, radioButton3;
    private RadioGroup radioGroup;
    Button buttonSuivant;
    private List<Question> listQuestions;


    /**
     * Methode newInstance()
     *
     * @return instance
     */
    public static Fragment newInstance() {
        return (new QuizzDetailsFragment());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewQuiz = (TextView) view.findViewById(R.id.nom_quiz);
        radioButton1 = (RadioButton) view.findViewById(R.id.radioBtn1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioBtn2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioBtn3);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGp1);
        System.out.println(radioGroup.toString());
        buttonSuivant = (Button) view.findViewById(R.id.buttonNext);

        Bundle bundle = this.getArguments();
        final int myInt = bundle.getInt("idQuiz");

        setQuestionView(myInt);

        //Enfin on met un écouteur d'évènement sur notre listView
        buttonSuivant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("Radio selectionné" + radioGroup.getCheckedRadioButtonId());

                RadioButton answer = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
                radioGroup.clearCheck();
                Log.d("TestRadio", currentQuestion.getReponse() + answer.getText());
                if (currentQuestion.getReponse().equals(answer.getText())) {
                    score++;
                    Log.d("Your score", "Your score" + score);
                    System.out.println("Your score" + score);
                }
                if (questionId < 5) {
                    currentQuestion = listQuestions.get(questionId);
                    setQuestionView(myInt);
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("score", score);
                    Fragment fragment = QuizzResultatFragment.newInstance();
                    fragment.setArguments(bundle1);
                    getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.layoutFicheDetail, fragment).commit();

                }
            }
        });
    }

    /**
     * Methode onClick
     *
     * @param view
     */
    public void onClick(View view) {
        Bundle bundle = this.getArguments();
        final int myInt = bundle.getInt("idQuiz");

        System.out.println("Radio selectionné" + radioGroup.getCheckedRadioButtonId());

        RadioButton answer = (RadioButton) view.findViewById(radioGroup.getCheckedRadioButtonId());
        radioGroup.clearCheck();
        Log.d("TestRadio", currentQuestion.getReponse() + answer.getText());
        if (currentQuestion.getReponse().equals(answer.getText())) {
            score++;
            Log.d("Your score", "Your score" + score);
            System.out.println("Your score" + score);
        }
        if (questionId < 5) {
            currentQuestion = listQuestions.get(questionId);
            setQuestionView(myInt);
        } else {
            Bundle bundle1 = new Bundle();
            bundle1.putInt("score", score);
            Fragment fragment = QuizzResultatFragment.newInstance();
            fragment.setArguments(bundle1);
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.layoutFicheDetail, fragment).commit();

        }
    }

    /**
     * Creation
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //AFFICHE LA VUE DU DETAIL DE GLOSSAIRE
        super.onCreate(savedInstanceState);


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

    /**
     * Création de la vue
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizz_details_fragment, container, false);
        return view;
    }

    private void setQuestionView(int idQuestion) {
        listQuestions = EdwinDatabase.getAppDatabase(textViewQuiz.getContext()).questionDao().findAllQuestions();
        currentQuestion = listQuestions.get(questionId);

        textViewQuiz.setText(currentQuestion.getIntitule());
        radioButton1.setText(currentQuestion.getChoix1());
        radioButton2.setText(currentQuestion.getChoix2());
        radioButton3.setText(currentQuestion.getChoix3());
        questionId++;
    }
}
