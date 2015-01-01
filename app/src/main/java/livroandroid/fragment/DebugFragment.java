package livroandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ricardo Lecheta on 22/09/2014.
 */
public class DebugFragment extends Fragment {
    protected static final String TAG = "livroandroid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log(getClassName() + ".onCreate().");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log(getClassName() + ".onCreateView().");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log(getClassName() + ".onActivityCreated().");
    }

    @Override
    public void onStart() {
        super.onStart();
        log(getClassName() + ".onStart().");
    }

    @Override
    public void onResume() {
        super.onResume();
        log(getClassName() + ".onResume().");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log(getClassName() + ".onSaveInstanceState().");
    }

    @Override
    public void onPause() {
        super.onPause();
        log(getClassName() + ".onPause().");
    }

    public void onStop() {
        super.onStop();
        log(getClassName() + ".onStop().");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log(getClassName() + ".onDetach().");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log(getClassName() + ".onDestroyView().");
    }

    public void onDestroy() {
        super.onDestroy();
        log(getClassName() + ".onDestroy().");
    }

    public String getClassName() {
        // Retorna o nome da classe sem o pacote
        Class cls = ((Object) this).getClass();
        String s = cls.getSimpleName();
        return s;
    }

    protected void log(String msg) {
        Log.d(TAG, msg);
    }
}
