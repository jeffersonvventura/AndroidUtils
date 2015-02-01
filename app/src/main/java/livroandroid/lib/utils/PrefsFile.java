package livroandroid.lib.utils;

import android.content.Context;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 * Salva arquvos no formato chave e valor.
 *
 * Se chamar o método setString("nome","Ricardo"), vai salvar o arquivo
 *
 * /data/data/app/files.nome.txt
 *
 * </pre>
 */
public class PrefsFile {
    public static final String PREF_ID = "prefs";
    private static final String TAG = "PrefsFile";

    public static void setBoolean(Context context, String flag, boolean on) {
        setString(context, flag, on ? "1" : "0");
    }

    public static boolean getBoolean(Context context, String flag) {
        String s = getString(context, flag);
        boolean on = "1".equals(s);
        return on;
    }

    public static void setInteger(Context context, String flag, int valor) {
        setString(context, flag, String.valueOf(valor));
    }

    public static int getInteger(Context context, String flag) {
        String s = getString(context, flag);
        if (s != null) {
            return Integer.parseInt(s);
        }
        return 0;
    }

    public static void setString(Context context, String flag, String valor) {
        try {
            File f = context.getFileStreamPath(flag);
            if (f.exists()) {
                Log.d(TAG, "PrefsFile.setString delete file: " + f);
                f.delete();
            }
            FileOutputStream out = context.openFileOutput(flag, Context.MODE_PRIVATE);
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeUTF(valor);
            dataOut.close();
            Log.d(TAG, "PrefsFile.setString valor: " + valor);
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static String getString(Context context, String flag) {
        try {
            File f = context.getFileStreamPath(flag);
            Log.d(TAG, "PrefsFile.getString file: " + f);
            if (f.exists()) {
                FileInputStream in = context.openFileInput(flag);
                DataInputStream dataIn = new DataInputStream(in);
                String s = dataIn.readUTF();
                Log.d(TAG, "PrefsFile.getString s: " + s);
                return s;
            }
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
        return null;
    }
}


