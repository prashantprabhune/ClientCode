package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import JiraAPI.Result.SampleJson;
import call.restapi.CircleCi_Testmetadata;
import call.restapi.Zql_Api;
import CircleCi.Result.Result;
import CircleCi.Result.Test;
import zapi.TestManagement;

public class TestManagement_Integration {

	public Map<String, String> compare_Jira_and_CirccleCi(Map<String, String> circleCiR, Map<String, String> jiraR) {
		Map<String, String> ComparedResult = new HashMap<String, String>();

		for (Map.Entry<String, String> entry : circleCiR.entrySet()) {
			for (Map.Entry<String, String> entry2 : jiraR.entrySet()) {
				if (entry.getKey().contains(entry2.getKey())) {
					ComparedResult.put(entry2.getValue(), entry.getValue());
				}
			}
		}
		return ComparedResult;
	}

	public static void main(String[] args) throws Exception {
	     long startTime = System.currentTimeMillis();

		TestManagement_Integration testMgmnt = new TestManagement_Integration();
		TestManagement testCycle = new TestManagement();
		String response=Zql_Api.zql_api();
		System.out.println("--------------------Execution Start----------------------");

		// Call Jira api and circleci api
		
		Map<String, String> circleCi_Reult = new HashMap<String, String>();
		StringBuffer json = CircleCi_Testmetadata.circleCi_TestMetaData();
		Gson gson= new Gson();
			Result result= gson.fromJson(json.toString(), Result.class);
//			System.out.println(result.getTests());
			
			if(result != null){
				for(Test t : result.getTests()){
//				System.out.println(t.getName()+"  "+t.getResult());
				circleCi_Reult.put(t.getName(), t.getResult());
				}//		System.out.println("I m in main method " + json);

			}
//			System.out.println("Successfull called Circi");
		
//		
		/***** Jira json convert to java object ******/

		Map<String, String> jira_Result = new HashMap<String, String>();
		Map<Object, Object> jira_json_result = SampleJson.getJiraSummary();

		for (Map.Entry<Object, Object> e : jira_json_result.entrySet()) {
			String testCase_name = (String) e.getKey();
			String isssue_id_from_jira = (String) e.getValue();
			jira_Result.put(testCase_name, isssue_id_from_jira);
//			System.out.println(e.getKey()+" Issue id"+e.getValue());
		}
//		System.out.println("Successfull called Circi");

		// ********************* Create cycle************************************
				String testCycleID = testCycle.createCycle();
				System.out.println("Cycle created \n"+"Test Cycle ID : " + testCycleID);
				String CycleId = testCycleID;

//				System.out.println("****************************Cycle created***********");
				
		/***** call compare result of jira and circle ******/

		Map<String, String> final_result_fo_ci_jira = testMgmnt.compare_Jira_and_CirccleCi(circleCi_Reult, jira_Result);

		String[] keys = new String[final_result_fo_ci_jira.size()];
		String[] IssueIDs = new String[final_result_fo_ci_jira.size()];
		int index = 0;
		for (Entry<String, String> mapEntry : final_result_fo_ci_jira.entrySet()) {
			keys[index] = mapEntry.getKey();
			IssueIDs[index] = mapEntry.getKey();
//			System.out.println(mapEntry.getKey()+" **********    "+mapEntry.getValue());
			index++;

		}
		for (int i = 0; i < IssueIDs.length; i++) {

			String[] issueIDs = IssueIDs;
			
			// ********************* Add to test************************************

			String addTestCycleRes = testCycle.addTestToCycle(testCycleID, issueIDs);
//			System.out.println("Add Test To Cycle Res : " + addTestCycleRes);

			System.out.println("********************Test added to cycle*******************");
			
			// *********************Execution of Cycle************************************

			TestManagement testExecutionManagement = new TestManagement();
			String executionID = testExecutionManagement.getExeutionID(testCycleID, issueIDs[i]);
//			System.out.println(
//					"Execution ID of Issue ID (" + issueIDs + ") from Cycle ID (" + testCycleID + ") : " + executionID);

//			System.out.println("********************Execution of Cycle*******************");
			int updateStatus = 0;
			for (Map.Entry<String, String> entry : final_result_fo_ci_jira.entrySet()) {
				if (entry.getKey().equals(issueIDs[i]) && entry.getValue().equals("success")) {
					updateStatus = 1;
					String updateExeRes = testExecutionManagement.updateBulkExecutionResult(executionID, updateStatus);
//					System.out.println("Update Execution Response : " + updateExeRes);

				} else if (entry.getKey().equals(issueIDs[i]) && entry.getValue().equals("failure")) {
					updateStatus = 2;
					String updateExeRes = testExecutionManagement.updateBulkExecutionResult(executionID, updateStatus);
//					System.out.println("Update Execution Response : " + updateExeRes);
				}
			}
		}
		System.out.println("Successfully execute");
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Execution time:"+elapsedTime);

	}
}
