package call.restapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import JiraAPI.Result.SampleJson;
import zapi.PropertyFileReader;

public class Zql_Api {

	public static String zql_api() throws IOException{
		String url = "https://loezatom81.atlassian.net/rest/api/2/project" ;
		String username="loeza.tom81@gmail.com";
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

			// print result
//			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}
		String response=response_temp.toString();
//		System.out.println(response);
		return response;
	}
	
	public static int getProjectID(String response) throws IOException, JSONException{
		int projectID=0;
		String b = response;
		String project_name=null;
		String project_id=null;
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String projName = propertyFileReader.getProjectName();
		JSONArray arr = new JSONArray(b);
		Map<String,String> result = new HashedMap();
		for(int i = 0; i < arr.length(); i++){
			result.put(arr.getJSONObject(i).getString("name"), arr.getJSONObject(i).getString("id"));
		}
	
		 for (Entry<String, String> e : result.entrySet()) {
				project_name = (String) e.getKey();
				project_id = (String) e.getValue();
				if (projName.equals(project_name)) {
					projectID = Integer.parseInt(project_id);
//					System.out.println(projectID);
					break;
				}
	

		}
		System.out.println("Zql api");

		return projectID;
	}
	
//	public static void main(String []args) throws IOException, JSONException{
//		Zql_Api zql=new Zql_Api();
//		String response=Zql_Api.zql_api();
//		System.out.println(Zql_Api.getProjectID(response));
//	}

	
}
