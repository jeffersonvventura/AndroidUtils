package livroandroid.lib.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Classe utilitária para enviar intents
 */
public class IntentUtils {

    private static final String TAG = "IntentUtils";

    public static void openBrowser(Context context, String url) {
		try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "openBrowser() - ActivityNotFoundException [\"+url+\"]: " + e.getMessage());
        }
	}

    public static void showVideo(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/*");
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "showVideo() - ActivityNotFoundException ["+url+"]: " + e.getMessage());
        }
    }
}
