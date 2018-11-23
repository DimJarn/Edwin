package fr.eseo.pfe.edwin.Main;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.eseo.pfe.edwin.Util.LogUtil;

import static fr.eseo.pfe.edwin.Util.LogUtil.makeLogTag;

public class MainFragment extends Fragment {

    private static final String TAG = makeLogTag(MainFragment.class);

    /**
     * Inflates the layout and binds the view via ButterKnife.
     * @param inflater the inflater
     * @param container the layout container
     * @param layout the layout resource
     * @return the inflated view
     */
    public View inflateAndBind(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container, false);


        //ButterKnife.bind(this, view);

        LogUtil.logD(TAG, ">>> view inflated");
        return view;
    }
}
