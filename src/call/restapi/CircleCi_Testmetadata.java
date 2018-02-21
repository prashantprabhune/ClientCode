package call.restapi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import CircleCi.Result.Result;
import CircleCi.Result.Test;

public class CircleCi_Testmetadata {
	public static StringBuffer circleCi_TestMetaData() throws Exception {
		String build_num = CircleCi_Build_Number.circleCI_Build_Num();
//		System.out.println("Build no " + build_num);
		String url = "https://circleci.com/api/v1.1/project/github/prashantprabhune/SampleDjangoDemo/" + build_num
				+ "/tests?";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("circle-token", "ea91806deec662ab62f74185eb0e7a4c2822c4d1");
		int responseCode = con.getResponseCode();
//		System.out.println("GET Response Code :: " + responseCode);
		StringBuffer response = new StringBuffer();
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
//			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
		System.out.println("CircliCI Test metadata");

		return response;
	}
//	public static void main(String args[]) throws Exception{
//		System.out.println(CircleCi_Testmetadata.circleCi_TestMetaData());
//	}
}
