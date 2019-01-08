package fr.eseo.pfe.edwin;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.Question;

public class QuizzDetailsFragment extends Fragment {

    public static final String YOUR_SCORE = "Your score";
    int questionId = 0;
    int score = 0;
    Question currentQuestion;
    TextView textViewQuiz;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    private RadioGroup radioGroup;
    Button buttonSuivant;
    private List<Question> listQuestions;
    private TextView numeroQuestionTextView;
    private TextView nomQuiz;

    ProgressBar androidProgressBar;
    int progressStatusCounter = 0;
    TextView textView;
    Handler progressHandler = new Handler();

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
        numeroQuestionTextView = (TextView) view.findViewById(R.id.quiz_question_numero);
        nomQuiz = (TextView) view.findViewById(R.id.nom_du_quiz);

        androidProgressBar = (ProgressBar) view.findViewById(R.id.horizontal_progress_bar);
        //Start progressing bar
        new Thread(new Runnable() {
            public void run() {
                while (progressStatusCounter < 100) {
                    progressStatusCounter = 0;
                    androidProgressBar.setProgress(progressStatusCounter);
                    progressHandler.post(new Runnable() {
                        public void run() {
                            switch (questionId ) {
                                case 1:
                                    progressStatusCounter = 0;
                                    androidProgressBar.setProgress(progressStatusCounter);
                                    break;
                                case 2:
                                    progressStatusCounter = 20;
                                    androidProgressBar.setProgress(progressStatusCounter);
                                    break;
                                case 3:
                                    progressStatusCounter = 40;
                                    androidProgressBar.setProgress(progressStatusCounter);
                                    break;
                                case 4:
                                    progressStatusCounter = 60;
                                    androidProgressBar.setProgress(progressStatusCounter);
                                    break;
                                case 5:
                                    progressStatusCounter = 80;
                                    androidProgressBar.setProgress(progressStatusCounter);
                                    break;
                                default:
                                    progressStatusCounter = 0;
                                    androidProgressBar.setProgress(progressStatusCounter);
                            }
                        }
                    });
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Bundle bundleIdQuiz = this.getArguments();
        final int idQuiz = bundleIdQuiz.getInt("idQuiz");

        String titreQuiz = EdwinDatabase.getAppDatabase(textViewQuiz.getContext()).quizDao()
                .findQuizFromId(idQuiz).getNomQuiz();
        nomQuiz.setText(titreQuiz);

        listQuestions = EdwinDatabase.getAppDatabase(textViewQuiz.getContext()).questionDao()
                .findQuestionFromIdQuiz(idQuiz);
        currentQuestion = listQuestions.get(questionId);

        radioButton1 = (RadioButton) view.findViewById(R.id.radioBtn1);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioBtn2);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioBtn3);
        buttonSuivant = (Button) view.findViewById(R.id.buttonNext);

        setQuestionView();

        //Enfin on met un écouteur d'évènement sur notre listView
        buttonSuivant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RadioButton answer = (RadioButton) getActivity().findViewById(radioGroup
                        .getCheckedRadioButtonId());
                int idRadioGroup = radioGroup.getCheckedRadioButtonId();
                radioGroup.clearCheck();

                if (idRadioGroup == -1) {
                    // no radio buttons are checked
                    Toasty.error(getContext(), "Aucune réponse selectionnée.", Toast
                            .LENGTH_SHORT, true).show();
                } else {
                    // one of the radio buttons is checked
                    if (currentQuestion.getReponse().equals(answer.getText())) {
                        score++;
                        Log.d(YOUR_SCORE, YOUR_SCORE + score);
                    }
                    if (questionId < 5) {
                        currentQuestion = listQuestions.get(questionId);
                        setQuestionView();
                    } else {
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("score", score);
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("idQuiz", idQuiz);
                        Fragment fragment = QuizzResultatFragment.newInstance();
                        fragment.setArguments(bundle1);
                        fragment.setArguments(bundle2);
                        getFragmentManager().beginTransaction().replace(R.id.layoutFicheDetail,
                                fragment).commit();

                    }
                }
            }

        });
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
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGp1);
        return view;
    }

    private void setQuestionView() {


        textViewQuiz.setText(currentQuestion.getIntitule());
        numeroQuestionTextView.setText("Question n°" + (questionId + 1) + " :");
        radioButton1.setText(currentQuestion.getChoix1());
        radioButton2.setText(currentQuestion.getChoix2());
        radioButton3.setText(currentQuestion.getChoix3());

        questionId++;
    }

}
