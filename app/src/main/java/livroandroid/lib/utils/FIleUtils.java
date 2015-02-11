package livroandroid.lib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ricardo on 11/02/15.
 */
public class FIleUtils {

    /**
     * Le da pasta /res/raw
     *
     * @param context
     * @param raw R.raw.arquivo
     * @return
     */
    public static InputStream readRawFile(Context context, int raw) {
        Resources resources = context.getResources();
        InputStream in = resources.openRawResource(raw);
        return in;
    }

    public static String readRawFileString(Context context, int raw, String charset) throws IOException {
        Resources resources = context.getResources();
        InputStream in = resources.openRawResource(raw);
        return IOUtils.toString(in,charset);
    }

    /**
     * Le da pasta assets
     *
     * @param context
     * @param fileName nome do arquivo
     * @return
     * @throws IOException
     */
    public static InputStream readAssetsFile(Context context, String fileName) throws IOException {
        AssetManager assets = context.getAssets();
        InputStream in = assets.open(fileName);
        return in;
    }

    public static String readAssetsFile(Context context, String fileName, String charset) throws IOException {
        AssetManager assets = context.getAssets();
        InputStream in = assets.open(fileName);
        String s = IOUtils.toString(in, charset);
        return s;
    }

    /**
     * Retorna o caminho do arquivo para ser salvo na memória interna.
     *
     * Obs: Apenas retorna o caminho do arquivo, mas não o cria.
     *
     * Ex: /data/data/br.com.livroandroid.carros/files/arquivoi.txt
     *
     * @param context
     * @param name
     * @return
     */
    public static File getPrivateFile(Context context,String name) {
        File file = context.getFileStreamPath(name);
        return file;
    }

    /**
     * Retorna o Outputstream para salvar o arquivo na memória interna.
     *
     * Ex: /data/data/br.com.livroandroid.carros/files/arquivoi.txt
     *
     * @param context
     * @param name
     * @return
     */
    public static OutputStream getPrivateFileOutput(Context context,String name) throws FileNotFoundException {
        // Cria o arquivo e retorna o OutputStream
        FileOutputStream out = context.openFileOutput(name, Context.MODE_PRIVATE);
        return out;
    }

    /**
     * Retorna a InputStream para ler o arquivo da memória interna.
     *
     * Ex: /data/data/br.com.livroandroid.carros/files/arquivoi.txt
     *
     * @param context
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getPrivateFileInputStream(Context context,String name) throws FileNotFoundException {
        FileInputStream in = context.openFileInput(name);
        return  in;
    }
}