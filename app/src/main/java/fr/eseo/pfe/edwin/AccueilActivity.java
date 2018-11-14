package fr.eseo.pfe.edwin;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class AccueilActivity extends Fragment implements View.OnClickListener {

    public static AccueilActivity newInstance() {
        return (new AccueilActivity());
    }

    Fragment AideActivity;
    FragmentActivity listener;
    private Fragment fragmentAide;
    private Fragment fragmentAPropos;
    private Fragment fragmentFiches;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.accueil, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonHelp = (Button) view.findViewById(R.id.buttonNeedHelp);
        buttonHelp.setOnClickListener(this);

        Button buttonYourOperation = (Button) view.findViewById(R.id.buttonYourOperation);
        buttonYourOperation.setOnClickListener(this);

        Button buttonMoreInfo = (Button) view.findViewById(R.id.buttonMoreInfo);
        buttonMoreInfo.setOnClickListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYourOperation:
                this.showFichesFragment();
                break;
            case R.id.buttonNeedHelp:
                this.showAideFragment();
                break;
            case R.id.buttonMoreInfo:
                this.showAProposFragment();
                break;
        }
    }

    // Methode générique permettant de gérer l'affichage des fragment dans l'element du layout activity_main_frame_layout
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    private void showAProposFragment(){
        if (this.fragmentAPropos == null) this.fragmentAPropos = AProposActivity.newInstance();
        this.startTransactionFragment(this.fragmentAPropos);
    }

    private void showAideFragment(){
        if (this.fragmentAide == null) this.fragmentAide = fr.eseo.pfe.edwin.AideActivity.newInstance();
        this.startTransactionFragment(this.fragmentAide);
    }

    private void showFichesFragment(){
        if (this.fragmentFiches == null) this.fragmentFiches = FichesActivity.newInstance();
        this.startTransactionFragment(this.fragmentFiches);
    }
}