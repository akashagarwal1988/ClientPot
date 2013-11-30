package googleSafeBrowsing;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class GoogleSafeBrowsingJava {

	static String apiKey = "ABQIAAAAdzYAOuDjmY34uWJXMCbQtRSBtTh7AjWOq7gZFDqLMQra1r-88g";
	
	
	
	
	
	public static String scanURL(String url) throws IOException{
		
		
		URLEncoder.encode(url, "UTF-8");
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"https://sb-ssl.google.com/safebrowsing/api/lookup?client=firefox&apikey="+ apiKey +"&appver=1.5.2&pver=3.0&url=" + url);
		HttpResponse response = httpClient.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();
		System.out.println(statusLine.getStatusCode());
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			
			try {
				String myString = IOUtils.toString(instream, "UTF-8");
				if(statusLine.getStatusCode()!=200 || statusLine.getStatusCode()!=204){
					myString = "Error occurred while scanning";
				}
				return myString;
			} finally {
				instream.close();
			}
		}
	
		return null;
	}
	
}
