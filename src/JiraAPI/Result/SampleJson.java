package JiraAPI.Result;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import call.restapi.Jira_api;



 public class SampleJson {
	
	 public static  Map<Object, Object> getJiraSummary() throws Exception{
		 JSONParser parser = new JSONParser();
			Map<Object, Object> data = new HashMap<Object,Object>();
	        try {
	        	 String str1 = Jira_api.jira_api();
	        	 Object obj = parser.parse(str1);
	             JSONObject jsonObject = (JSONObject) obj;
	             JSONArray issues = (JSONArray) jsonObject.get("issues");
	             for(Object o:issues){
	                 Object o1 = ((JSONObject) o).get("fields");
	                 data.put(((JSONObject) o1).get("summary"),((JSONObject) o).get("id"));
	             }
	         }catch (FileNotFoundException e) {
	            e.printStackTrace();
	         }catch (IOException e) {
	            e.printStackTrace();
	        }
			System.out.println("Jira json run");

			return data;
					
		}
//	 public static void main(String []args) throws Exception{
////		 SampleJson sj=new SampleJson();
//		 Map<Object, Object> sample=SampleJson.getJiraSummary();
//		 for (Map.Entry<Object, Object> e : sample.entrySet()) {
//				String str1 = (String) e.getKey();
//				String str2 = (String) e.getValue();
//				System.out.println(str1+" IssueId   "+str2);
//				
//			}
//	 }

}



