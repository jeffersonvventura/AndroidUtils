package livroandroid.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpHelper {
	private static final String TAG = "Http";
	public static boolean LOG_ON = true;

	public static String doGet(String url) throws IOException {
		return doGet(url,null,"UTF-8");
	}
	
	public static String doGet(String url, Map<String,String> params,String charset) throws IOException {
		String queryString = HttpHelper.getQueryString(params,null);
		if(queryString != null && queryString.trim().length() > 0){
			url += "?" + queryString;
		}

		if (LOG_ON) {
			Log.d(TAG, ">> Http.doGet: " + url);
		}

		URL u = new URL(url);
		HttpURLConnection conn = null;
		String s = null;
		try {
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			InputStream in = conn.getInputStream();
			s = IOUtils.toString(in, charset);
			if (LOG_ON) {
				Log.d(TAG, "<< Http.doGet: " + s);
			}
			in.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		
		return s;
	}

	public static String doPost(String url, Map<String,String> params, String charset) throws IOException {
        String queryString = HttpHelper.getQueryString(params, charset);
		byte[] bytes = params != null ? queryString.getBytes(charset) : null;
		if(LOG_ON){
			Log.d(TAG, "Http.doPost: " + url + "?" + params);
		}
		return doPost(url, bytes, charset);
	}

	public static String doPost(String url, byte[] params, String charset) throws IOException {
		if (LOG_ON) {
			Log.d(TAG, ">> Http.doPost: " + url);
		}

		URL u = new URL(url);
		HttpURLConnection conn = null;
		String s = null;
		try {
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			
			if(params != null) {
				OutputStream out = conn.getOutputStream();
				out.write(params);
				out.flush();
				out.close();
			}
			
			InputStream in = conn.getInputStream();
			s = IOUtils.toString(in, charset);
			if (LOG_ON) {
				Log.d(TAG, "<< Http.doPost: " + s);
			}
			in.close();
		} catch (IOException e) {
			throw e;
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
        return s;
	}

	public static Bitmap doGetBitmap(String url) throws IOException {
		if (LOG_ON) {
			Log.d(TAG, ">> Http.doGet: " + url);
		}
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.connect();
		InputStream in = conn.getInputStream();
		byte[] bytes = IOUtils.toBytes(in);
		if (LOG_ON) {
			Log.d(TAG, "<< Http.doGet: " + bytes);
		}
		in.close();
		conn.disconnect();
		Bitmap bitmap = null;
		if(bytes != null) {
			bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		}
		return bitmap;
	}
	
	/**
	 * Retorna a QueryString para 'GET' 
	 * 
	 * @param params
	 * @return
	 * @throws java.io.IOException
	 */
	public static String getQueryString(Map<String,String> params, String charsetToEncode) throws IOException {
		if (params == null || params.size() == 0) {
			return null;
		}
		String urlParams = null;
		for (String chave : params.keySet()) {
			Object objValor = params.get(chave);
			if (objValor != null) {
				String valor = objValor.toString();
				if(charsetToEncode != null) {
					valor = URLEncoder.encode(valor, charsetToEncode);
				}
				urlParams = urlParams == null ? "" : urlParams + "&";
				urlParams += chave + "=" + valor;
			}
		}
		return urlParams;
	
	}

    public static void bingo3() {


    }
}
