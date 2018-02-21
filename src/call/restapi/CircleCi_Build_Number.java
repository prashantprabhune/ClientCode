package call.restapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CircleCi_Build_Number {

	public static String circleCI_Build_Num() throws Exception {
		String url = "https://circleci.com/api/v1.1/project/github/prashantprabhune/SampleDjangoDemo?";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("circle-token", "ea91806deec662ab62f74185eb0e7a4c2822c4d1");
		con.setRequestProperty("limit", "1");
		con.setRequestProperty("offset", "0");
		con.setRequestProperty("filter", "default");

		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		// print in String
//		System.out.println(response.toString());
		String result = response.substring(1, response.length() - 1);
		// Read JSON response and print
		JSONObject myResponse = new JSONObject(result.toString());
//		System.out.println("result after Reading JSON Response");
//		System.out.println("build_num" + myResponse.getInt("build_num"));
		String build_num = String.valueOf(myResponse.getInt("build_num"));
		System.out.println("CircliCI build number");
		return build_num;

	}
//	public static void main(String args[]) throws Exception{
//		System.out.println(CircleCi_Build_Number.circleCI_Build_Num());
//	}
}