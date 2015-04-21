package livroandroid.lib.wear;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * Texto sobre Assets da documentação oficial:
 *
 * Typically, your image should not exceed
 * 320x320 and if you want to have zoom and parallax effect in your app, limit the size of your
 * image to 640x400. Resize your image before transferring to your wearable device.
 */
public class WearBitmapUtil {
    // Cria um Assert a partir de um Bitmap
    public static Asset getAssetFromBitmap(Bitmap bitmap) {
        final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return Asset.createFromBytes(byteStream.toByteArray());
    }

    public static Bitmap getBitmapFromAsset(GoogleApiClient googleApiClient,Asset asset) {
        if (asset == null) {
            return null;
        }
        // convert asset into a file descriptor and block until it's ready
        InputStream in = Wearable.DataApi.getFdForAsset(
                googleApiClient, asset).await().getInputStream();
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }
}
