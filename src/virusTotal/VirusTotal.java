package virusTotal;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;




public class VirusTotal {

	String apiKey;
	Gson gson = new Gson();
	private final String USER_AGENT = "Mozilla/5.0";
	
	public VirusTotal(String apiKey) {
		this.apiKey = apiKey;
	}


	public String sendSubmitURls(String url) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"https://www.virustotal.com/vtapi/v2/url/scan");
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
		params.add(new BasicNameValuePair(
				"url",
				url));
		params.add(new BasicNameValuePair("apikey",
				apiKey));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpClient.execute(httppost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				String myString = IOUtils.toString(instream, "UTF-8");
				return myString;
			} finally {
				instream.close();
			}
		}
		return null;

	}
	
	public String retreiveURLscanReport(String url) throws ClientProtocolException, IOException{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://www.virustotal.com/vtapi/v2/url/report");
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(2);
		params.add(new BasicNameValuePair(
				"resource",
				url));
		params.add(new BasicNameValuePair("apikey",
				apiKey));
		httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		HttpResponse response = httpClient.execute(httppost);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				String myString = IOUtils.toString(instream, "UTF-8");
				return myString;
			} finally {
				instream.close();
			}
		}
		return null;
	}
	
}
