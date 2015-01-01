package livroandroid.activity;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ricardo Lecheta on 22/09/2014.
 */
public class BaseActivity extends DebugActivity {

    private static final String TAG = "livroandroid";

    private Context getContext() {
        return this;
    }

    protected void log(String msg) {
        Log.d(TAG, msg);
    }

    protected void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void alert(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}
