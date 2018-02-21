package zapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thed.zephyr.cloud.rest.ZFJCloudRestClient;
import com.thed.zephyr.cloud.rest.client.JwtGenerator;

import call.restapi.CircleCi_Build_Number;
import call.restapi.Zql_Api;

public class TestManagement {

	public String createCycle() throws Exception {

		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String zephyrBaseUrl = propertyFileReader.getZephyrBaseUrl();
		String accessKey = propertyFileReader.getAccessKey();
		String secretKey = propertyFileReader.getSecretKey();
		String userName = propertyFileReader.getUsername();

		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName)
				.build();
		System.out.println();
		/** Declare the Variables here **/

		GetZQLFieldValues getZQLFieldValue = new GetZQLFieldValues();
		String zqlRespnse = getZQLFieldValue.getZQLFieldValues();

		String response_zapi=Zql_Api.zql_api();
		int projectId = Zql_Api.getProjectID(response_zapi);
//		System.out.println("----------"+projectId);
		int versionId = getZQLFieldValue.getFixVersionID(zqlRespnse);
		
		String cycleName = propertyFileReader.getTestCycleName();
		System.out.println("Cycle Name :"+cycleName+" - " +CircleCi_Build_Number.circleCI_Build_Num()+"\n----------------------------------------------------------");
		String cycleDescription = propertyFileReader.getTestCycleDescription();
		String build = propertyFileReader.getTestBuild();
		String environment = propertyFileReader.getTestEnvironment();

		String ZQLFieldValuesUri = zephyrBaseUrl + "/public/rest/api/1.0/cycle";

		/** Cycle Object created - DO NOT EDIT **/
		JSONObject createCycleObj = new JSONObject();
		createCycleObj.put("name", cycleName);
		createCycleObj.put("description", cycleDescription);
		createCycleObj.put("build", build);
		createCycleObj.put("environment", environment);
		createCycleObj.put("startDate", System.currentTimeMillis());
		createCycleObj.put("projectId", projectId);
		createCycleObj.put("versionId", versionId);

		StringEntity cycleJSON = null;
		try {
			cycleJSON = new StringEntity(createCycleObj.toString());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		URI uri = new URI(ZQLFieldValuesUri);
		int expirationInSec = 3600;
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
		String responseStr = null;
//		System.out.println(uri.toString());
//		System.out.println(jwt);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpPost createCycleReq = new HttpPost(uri);
		createCycleReq.addHeader("Content-Type", "application/json");
		createCycleReq.addHeader("Authorization", jwt);
		createCycleReq.addHeader("zapiAccessKey", accessKey);
		createCycleReq.setEntity(cycleJSON);

		try {
			response = restClient.execute(createCycleReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int statusCode = response.getStatusLine().getStatusCode();
//		System.out.println("Create cycle response code "+statusCode);
		String cycleId = "-1";

		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();
			try {
				responseStr = EntityUtils.toString(entity);
//				System.out.println("Create Cycle Response : " + responseStr);
				JSONObject cycleObj = new JSONObject(responseStr);
				cycleId = cycleObj.getString ("id");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new ClientProtocolException("Unexpected response status: " + statusCode);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			}
		}
//		System.out.println("----------"+cycleId);

		return cycleId;
	}

	

	public String addTestToCycle(String cycleID, String[] issueID)
			throws IOException, URISyntaxException, JSONException {

		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String zephyrBaseUrl = propertyFileReader.getZephyrBaseUrl();
		String accessKey = propertyFileReader.getAccessKey();
		String secretKey = propertyFileReader.getSecretKey();
		String userName = propertyFileReader.getUsername();

		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName)
				.build();

		/** Declare the Variables here **/

		GetZQLFieldValues getZQLFieldValue = new GetZQLFieldValues();
		String zqlRespnse = getZQLFieldValue.getZQLFieldValues();

		String response_zapi=Zql_Api.zql_api();
		int projectId = Zql_Api.getProjectID(response_zapi);
		
		int versionId = getZQLFieldValue.getFixVersionID(zqlRespnse);
		String method = propertyFileReader.getMethod();

		String addTestsUri = zephyrBaseUrl + "/public/rest/api/1.0/executions/add/cycle/" + cycleID;

		JSONObject addTestsObj = new JSONObject();
		addTestsObj.put("issues", issueID);
		addTestsObj.put("method", method);
		addTestsObj.put("projectId", projectId);
		addTestsObj.put("versionId", versionId);

		StringEntity addTestsJSON = null;
		try {
			addTestsJSON = new StringEntity(addTestsObj.toString());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		URI uri = new URI(addTestsUri);
		int expirationInSec = 360;
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
//		System.out.println(uri.toString());
//		System.out.println(jwt);
		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpPost addTestsReq = new HttpPost(uri);
		addTestsReq.addHeader("Content-Type", "application/json");
		addTestsReq.addHeader("Authorization", jwt);
		addTestsReq.addHeader("zapiAccessKey", accessKey);
		addTestsReq.setEntity(addTestsJSON);
		try {
			response = restClient.execute(addTestsReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int statusCode = response.getStatusLine().getStatusCode();
//		System.out.println(statusCode);
//		System.out.println(response.toString());
		String responseStr = null;
		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();
			try {
				responseStr = EntityUtils.toString(entity);
//				System.out.println("Add Test To Cycle Response : " + responseStr);
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
		return responseStr;
	}

	
	

	public String getExeutionID(String cycleID,String issueID)throws URISyntaxException, JSONException, IOException {

		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String zephyrBaseUrl = propertyFileReader.getZephyrBaseUrl();
		String accessKey = propertyFileReader.getAccessKey();
		String secretKey = propertyFileReader.getSecretKey();
		String userName = propertyFileReader.getUsername();

		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName).build();

		/** Declare the Variables here **/
		GetZQLFieldValues getZQLFieldValue = new GetZQLFieldValues();
		String zqlRespnse = getZQLFieldValue.getZQLFieldValues();
		String response_zapi=Zql_Api.zql_api();
		int projectId = Zql_Api.getProjectID(response_zapi);

		String executionID=null;
		String getListofExecutionsUri = zephyrBaseUrl + "/public/rest/api/1.0/executions?issueId="+issueID+"&projectId="+projectId;

		/** Declare the Variables here **/

		URI uri = new URI(getListofExecutionsUri);
		int expirationInSec = 3600;
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		String jwt = jwtGenerator.generateJWT("GET", uri, expirationInSec);
		String responseStr = null;
//		System.out.println(uri.toString());
//		System.out.println(jwt);

		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpGet getExecutionsReq = new HttpGet(uri);
		getExecutionsReq.addHeader("Content-Type", "application/json");
		getExecutionsReq.addHeader("Authorization", jwt);
		getExecutionsReq.addHeader("zapiAccessKey", accessKey);
		try {
			response = restClient.execute(getExecutionsReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int statusCode = response.getStatusLine().getStatusCode();
//		System.out.println(statusCode);
		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();

			try {
				responseStr = EntityUtils.toString(entity);
//				System.out.println(responseStr);

//				System.out.println("List Execution " + responseStr);

				JSONObject obj = new JSONObject(responseStr);
				JSONArray arr = obj.getJSONArray("executions");
				for (int i = 0; i < arr.length(); i++)
				{
					String cycleIDs = arr.getJSONObject(i).getJSONObject("execution").getString("cycleId");

					if(cycleIDs.equals(cycleID)){
//						System.out.println("Cycle ID matched");
						executionID = arr.getJSONObject(i).getJSONObject("execution").getString("id");
//						System.out.println("Execution ID : "+executionID);
					}
				}

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
		return executionID;
	}

	
	
	
	public String updateBulkExecutionResult(String executionID, int status)
			throws URISyntaxException, JSONException, Exception, IOException {

		PropertyFileReader propertyFileReader = new PropertyFileReader();
		String zephyrBaseUrl = propertyFileReader.getZephyrBaseUrl();
		String accessKey = propertyFileReader.getAccessKey();
		String secretKey = propertyFileReader.getSecretKey();
		String userName = propertyFileReader.getUsername();

		ZFJCloudRestClient client = ZFJCloudRestClient.restBuilder(zephyrBaseUrl, accessKey, secretKey, userName)
				.build();

		String updateExecutionUri = zephyrBaseUrl + "/public/rest/api/1.0/executions";

		/** Execution Object created - DO NOT EDIT **/
		JSONObject executionObj = new JSONObject();
		JSONArray executionsarr = new JSONArray();
		executionsarr.put(executionID);
		executionObj.put("executions", executionsarr);
		executionObj.put("status", status);
		executionObj.put("testStepStatusChangeFlag", true);
		executionObj.put("stepStatus", -1);

		StringEntity executionJSON = null;
		try {
			executionJSON = new StringEntity(executionObj.toString());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		URI uri = new URI(updateExecutionUri);
		int expirationInSec = 3600;
		JwtGenerator jwtGenerator = client.getJwtGenerator();
		String jwt = jwtGenerator.generateJWT("POST", uri, expirationInSec);
//
//		System.out.println(uri.toString());
//		System.out.println(jwt);
		HttpResponse response = null;
		HttpClient restClient = new DefaultHttpClient();
		HttpPost createCycleReq = new HttpPost(uri);
		createCycleReq.addHeader("Content-Type", "application/json");
		createCycleReq.addHeader("Authorization", jwt);
		createCycleReq.addHeader("zapiAccessKey", accessKey);
		createCycleReq.setEntity(executionJSON);
		try {
			response = restClient.execute(createCycleReq);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int statusCode = response.getStatusLine().getStatusCode();
//		System.out.println(statusCode);
		String responseStr = null;

		if (statusCode >= 200 && statusCode < 300) {
			HttpEntity entity = response.getEntity();

			try {
				responseStr = EntityUtils.toString(entity);
//				System.out.println(responseStr);
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
		return responseStr;
	}
	
	/**********Main method to check output***********/
//	public static void main(String []args) throws IOException, URISyntaxException, JSONException{
//		TestManagement tsm=new TestManagement();
//		tsm.createCycle();
//	}
}
