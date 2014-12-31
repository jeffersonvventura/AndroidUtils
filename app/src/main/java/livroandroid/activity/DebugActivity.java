package livroandroid.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by Ricardo Lecheta on 22/09/2014.
 */
public class DebugActivity extends ActionBarActivity {
    protected static final String TAG = "livroandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log( getClassName() + ".onCreate(): " + savedInstanceState);
    }
    protected void onStart() {
        super.onStart();
        log( getClassName() + ".onStart().");
    }
    protected void onRestart() {
        super.onRestart();
        log( getClassName() + ".onRestart().");
    }
    protected void onResume() {
        super.onResume();
        log( getClassName() + ".onResume().");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        log( getClassName() + ".onSaveInstanceState().");
    }

    protected void onPause() {
        super.onPause();
        log( getClassName() + ".onPause().");
    }
    protected void onStop() {
        super.onStop();
        log( getClassName() + ".onStop().");
    }
    protected void onDestroy() {
        super.onDestroy();
        log( getClassName() + ".onDestroy().");
    }
    private String getClassName() {
        // Retorna o nome da classe sem o pacote
        Class cls = ((Object) this).getClass();
        String s = cls.getSimpleName();
        return s;
    }

    protected void log(String msg) {
        Log.d(TAG,msg);
    }
}
