package livroandroid.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	private static final String TAG = "IOUtils";

	public static String toString(InputStream in, String charset)
			throws IOException {
		byte[] bytes = toBytes(in);
		String texto = new String(bytes, charset);
		return texto;
	}

	public static byte[] toBytes(InputStream in) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int len;
			while ((len = in.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}
			byte[] bytes = bos.toByteArray();
			return bytes;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
			return null;
		} finally {
			try {
				bos.close();
				in.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}
}
