package call.restapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Jira_api {
	public static void main(String[] args) {
//		try {
//			Jira_api.jira_api();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	public static String jira_api() throws Exception {
		String url = "https://sachinkate.atlassian.net/rest/api/2/search?issuetype=TEST" ;
		String username="sachinkatesat@gmail.com";
		String password="vdit@123";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		String encoded = Base64.getEncoder().encodeToString((username+":"+password).getBytes(StandardCharsets.UTF_8));  //Java 8
		con.setRequestProperty("Authorization", "Basic "+encoded);
		
		int responseCode = con.getResponseCode();
//		System.out.println("GET Response Code :: " + responseCode);
		StringBuffer response_temp = new StringBuffer();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response_temp.append(inputLine);
			}
			in.close();


		} else {
			System.out.println("GET request not worked");
		}
		String response=response_temp.toString();
//		System.out.println(response);
		System.out.println("Jira api ");

		return response;
	}
}