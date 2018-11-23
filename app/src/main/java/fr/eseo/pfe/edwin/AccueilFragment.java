package fr.eseo.pfe.edwin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;

/**
 * NEVER USE NOW
 * Fragment accueil
 */
public class AccueilFragment extends ListFragment implements View.OnClickListener {

    private Callback callback = dummyCallback;
    FragmentActivity listener;
    Fragment AideActivity;
    private Fragment fragmentAide;
    private Fragment fragmentAPropos;
    private Fragment fragmentFiches;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * onAttach(Context) is not called on pre API 23 versions of Android.
     * onAttach(Activity) is deprecated but still necessary on older devices.
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //onAttachToContext(context);
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (!(context instanceof Callback)) {
            throw new IllegalStateException("Activity must implement callback interface.");
        }
        callback = (Callback) context;
    }


    /**
     * A callback interface. Called whenever a item has been selected.
     */
    public interface Callback {
        void onItemSelected(String id);
    }

    /**
     * A dummy no-op implementation of the Callback interface. Only used when no active Activity is present.
     */
    private static final Callback dummyCallback = new Callback() {
        @Override
        public void onItemSelected(String id) {
        }
    };


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // notify callback about the selected list item

        //callback.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYourOperation:
                System.out.println("Test");
                //this.showFichesFragment();
                break;
            case R.id.buttonNeedHelp:
                System.out.println("Test");

                //this.showAideFragment();
                break;
            case R.id.buttonMoreInfo:
                System.out.println("Test");

                //this.showAProposFragment();
                break;
        }
    }


    /**
     * Deprecated on API 23 but still necessary for pre API 23 devices.
     *//*
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }
*/
    public static AccueilFragment newInstance() {
        return (new AccueilFragment());
    }


}
