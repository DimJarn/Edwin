package fr.eseo.pfe.edwin;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eseo.pfe.edwin.data.EdwinDatabase;
import fr.eseo.pfe.edwin.data.Quiz;

/**
 * Fragment Quiz, extends de Fragment
 * Affichage de la page Quiz et intégration du menu
 */
public class QuizFragment extends Fragment {
    FragmentActivity listener;
    private ListView mListView;

    public static QuizFragment newInstance() {
        return (new QuizFragment());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz, container, false);
        ListView listView = view.findViewById(R.id.listView);
        TextView emptyTextView = view.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyTextView);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = view.findViewById(R.id.listView);

        final List<Quiz> quizList = EdwinDatabase.getAppDatabase(view.getContext()).quizDao()
                .findAllQuiz();

        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<>();
        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;


        //On refait la manip plusieurs fois avec des données différentes pour former les items de
        // notre ListView
        for (Quiz quiz : quizList) {
            map = new HashMap<>();
            map.put("titre", quiz.getNomQuiz());
            //xmap.put("img", String.valueOf(R.drawable.logo_quiz));
            map.put("arrow", String.valueOf(R.drawable.logo_arrowright));
            listItem.add(map);
        }

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list
        // (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mSchedule = new SimpleAdapter(getContext(), listItem, R.layout.fiche_item,
                    new String[]{"titre", "arrow"}, new int[]{R.id.titre, R.id.fleche});
        }

        //On attribut à notre listView l'adapter que l'on vient de créer
        mListView.setAdapter(mSchedule);

        //Enfin on met un écouteur d'évènement sur notre listView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                int idQuiz = quizList.get(position).getIdQuiz();
                Bundle bundle = new Bundle();
                bundle.putInt("idQuiz", idQuiz);
                Fragment fragment = QuizzDetailsFragment.newInstance();
                fragment.setArguments(bundle);
                // Très important !!
                //la méthode .addToBackStack permet d'utiliser le bouton retour dans le fragment
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id
                        .layout_fragment_fiche_1, fragment).commit();
            }
        });
    }

}
