package fr.eseo.pfe.edwin;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ParametresDetailsFragment extends Fragment {
    FragmentActivity listener;

    public static ParametresDetailsFragment newInstance() {
        return (new ParametresDetailsFragment());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.parametres_details_fragment, container, false);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
