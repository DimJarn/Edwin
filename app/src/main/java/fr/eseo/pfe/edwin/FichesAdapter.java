package fr.eseo.pfe.edwin;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.eseo.pfe.edwin.data.FicheInformative;

public class FichesAdapter extends RecyclerView.Adapter<FichesAdapter.ViewHolder> {

    FichesActivity activity;

    List<FicheInformative> ficheInformativeList;

    public FichesAdapter(FichesActivity activity, List<FicheInformative> ficheInformativeList){
        this.activity = activity;
        this.ficheInformativeList = ficheInformativeList;
    }

    @NonNull
    @Override
    public FichesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowfiches, viewGroup, false);
        return new FichesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nomMaladie.setText(ficheInformativeList.get(i).getNomOperation());
        final FicheInformative ficheInformative = ficheInformativeList.get(i);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.clickFicheInformativeCard(ficheInformative);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ficheInformativeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        public TextView nomMaladie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            nomMaladie = itemView.findViewById(R.id.nom_maladie);
        }
    }
}
