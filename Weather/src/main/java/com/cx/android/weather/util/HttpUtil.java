package com.cx.android.weather.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * http操作工具类
 * @author chenxue
 *
 */
public class HttpUtil {
	private static final int readTime = 3000;
	private static final int connectTime = 3000;
	
	/**
	 * http请求，结果返回字符串
	 * @param urlStr 请求URL
	 * @return  String
	 */
	public static String requestString(String urlStr) {
		return requestByte(urlStr) == null ? null : new String(requestByte(urlStr));
	}
	
	/**
	 * http请求，结果返回byte数组
	 * @param urlStr 请求URL
	 * @return byte[]
	 */
	public static byte[] requestByte(String urlStr){
		byte[] result = null;
		HttpURLConnection con = null;

		try {
			con = getConnection(urlStr);
			if(con != null) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				InputStream is = con.getInputStream();
				if(con.getResponseCode() != HttpURLConnection.HTTP_OK)return null;

				int bytesRead = 0;
				byte[] buffer = new byte[1024];
				while ((bytesRead = is.read(buffer)) > 0){
					out.write(buffer,0,bytesRead);
				}

				out.close();
				result = out.toByteArray();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(con != null){
				con.disconnect();
			}
		}

		return result;
	}

	public static InputStream requestInputstream(String urlStr){
		HttpURLConnection con = null;
		InputStream is = null;
		try {
			con = getConnection(urlStr);
			if(con != null) {
				is = con.getInputStream();
				if(con.getResponseCode() != HttpURLConnection.HTTP_OK)return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if(con != null){
				con.disconnect();
			}
		}

		return is;
	}

	private static HttpURLConnection getConnection(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setConnectTimeout(connectTime);
		con.setReadTimeout(readTime);

		return con;
	}


}