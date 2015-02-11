package livroandroid.lib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;

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
}