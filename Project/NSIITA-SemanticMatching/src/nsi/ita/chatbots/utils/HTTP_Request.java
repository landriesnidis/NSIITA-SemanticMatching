package nsi.ita.chatbots.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP_Request {
	
	/**
	 * 利用HttpURLConnection实现Get方法
	 * @param strURL
	 * @return
	 */
	public static String SendHttp_Get(String strURL) {
		URL url;
		try {
			url = new URL(strURL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line,strData = "";																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																	
			while ((line = reader.readLine()) != null){
				strData += line;
			}
			return strData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
