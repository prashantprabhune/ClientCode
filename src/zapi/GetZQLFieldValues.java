package zapi;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;

public class GetZQLFieldValues {


	public String getZQLFieldValues() throws URISyntaxException, JSONException, IOException {

		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String zephyrBaseUrl = propertyFileReader.getZephyrBaseUrl();
		String accessKey = propertyFileReader.getAccessKey();
		String secretKey = propertyFileReader.getSecretKey();
		String userName = propertyFileReader.getUsername();

		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName)
				.build();
		String ZQLFieldValuesUri = zephyrBaseUrl + "/public/rest/api/1.0/zql/fields/values";

		URI uri = new URI(ZQLFieldValuesUri);
		int expirationInSec = 3600;
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
		String string = null;
//		System.out.println(uri.toString());
//		System.out.println(jwt);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpGet getZQLFieldValuesReq = new HttpGet(uri);
		getZQLFieldValuesReq.addHeader("Content-Type", "application/json");
		getZQLFieldValuesReq.addHeader("Authorization", jwt);
		getZQLFieldValuesReq.addHeader("zapiAccessKey", accessKey);
		try {
			response = restClient.execute(getZQLFieldValuesReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int statusCode = response.getStatusLine().getStatusCode();
//		System.out.println(statusCode);

		if (statusCode >= 200 && statusCode < 300) {

			try {
				HttpEntity entity = response.getEntity();
				string = EntityUtils.toString(entity);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}
		return string;
	}

////	public int getProjectID(String response) throws IOException, JSONException{
//
//		PropertyFileReader propertyFileReader = new PropertyFileReader();
//		String projectName = propertyFileReader.getProjectName();
//		int projectID = 0;
//		//String projectID=null;
//		JSONObject jsonObj = new JSONObject(response);
//		JSONArray projArr = jsonObj.getJSONObject("fields").getJSONArray("project");
//		for(int i = 0; i<projArr.length(); i++){
//			String projName = projArr.getJSONObject(i).getString("name");
//			if (projName.equals(projectName)) {
//				projectID = projArr.getJSONObject(i).getInt("id");
//				break;
//			}
//		}
//		return projectID;
//	}
	
	public int getFixVersionID(String response) throws IOException, JSONException {
		
		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String fixVersionName = propertyFileReader.getFixVersionName();
		int fixVerID=0;
		JSONObject jsonObj = new JSONObject(response);
		JSONArray projArr = jsonObj.getJSONObject("fields").getJSONArray("fixVersion");
		for(int i = 0; i<projArr.length(); i++){
			String fixVerName = projArr.getJSONObject(i).getString("name");
			if (fixVerName.equals(fixVersionName)) {
				fixVerID = projArr.getJSONObject(i).getInt("id");
				break;
			}
		}
		return fixVerID;
	}
}
