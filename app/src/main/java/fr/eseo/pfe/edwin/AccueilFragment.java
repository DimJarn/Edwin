package fr.eseo.pfe.edwin;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * NEVER USE NOW
 * Fragment accueil
 */
public class AccueilFragment extends ListFragment implements View.OnClickListener {

    FragmentActivity listener;

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
        if (context instanceof Activity) {
            this.listener = (FragmentActivity) context;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYourOperation:
                Log.d("AccueilFragmentClick", "Test value");
                break;
            case R.id.buttonNeedHelp:
                Log.d("AccueilFragmentClick", "Test value");
                break;
            case R.id.buttonMoreInfo:
                Log.d("AccueilFragmentClick", "Test value");
                break;
            default:
                Log.d("AccueilFragmentError", "Default value");
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
