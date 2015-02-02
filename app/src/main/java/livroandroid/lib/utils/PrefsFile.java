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

    public static void setBoolean(Context context, String chave, boolean on) {
        setString(context, chave, on ? "1" : "0");
    }

    public static boolean getBoolean(Context context, String chave) {
        String s = getString(context, chave);
        boolean on = "1".equals(s);
        return on;
    }

    public static void setInteger(Context context, String chave, int valor) {
        setString(context, chave, String.valueOf(valor));
    }

    public static int getInteger(Context context, String chave) {
        String s = getString(context, chave);
        if (s != null) {
            return Integer.parseInt(s);
        }
        return 0;
    }

    public static void setString(Context context, String chave, String valor) {
        try {
            File f = context.getFileStreamPath(chave);
            if (f.exists()) {
                Log.d(TAG, "PrefsFile.setString delete file: " + f);
                f.delete();
            }
            FileOutputStream out = context.openFileOutput(chave, Context.MODE_PRIVATE);
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeUTF(valor);
            dataOut.close();
            Log.d(TAG, "PrefsFile.setString valor: " + valor);
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    public static String getString(Context context, String chave) {
        try {
            File f = context.getFileStreamPath(chave);
            Log.d(TAG, "PrefsFile.getString file: " + f);
            if (f.exists()) {
                FileInputStream in = context.openFileInput(chave);
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


