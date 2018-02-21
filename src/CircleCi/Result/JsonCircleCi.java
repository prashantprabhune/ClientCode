package CircleCi.Result;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import call.restapi.CircleCi_Build_Number;
import call.restapi.CircleCi_Testmetadata;
import call.restapi.Jira_api;

public class JsonCircleCi {

	public Map<Object, Object> getTestMetaData() throws Exception {
		
		JSONParser parser = new JSONParser();
		Map<Object, Object> data = new HashMap<Object,Object>();
        try {
        	 StringBuffer str1 =CircleCi_Testmetadata.circleCi_TestMetaData();
        	 String str2=str1.toString();
//        	 System.out.println("********************");
        	 Object obj = parser.parse(str2);
             JSONObject jsonObject = (JSONObject) obj;
             JSONArray issues = (JSONArray) jsonObject.get("tests");
//             System.out.println("********************");
             for(Object o:issues){
                 Object o1 = ((JSONObject) o).get("name");
                 Object o2 = ((JSONObject) o).get("result");
                 data.put(o1,o2);
       
             }
         }catch (FileNotFoundException e) {
            e.printStackTrace();
         }catch (IOException e) {
            e.printStackTrace();
        }
		return data;
		
//		JSONParser parser = new JSONParser();
//		Map<Object, Object> data = new HashMap<Object, Object>();
//		try {
//			System.out.println(Jira_api.jira_api());
//			
//			System.out.println("Ith alay");
//			JSONObject obj = (JSONObject)new JSONParser().parse(s);
//			JSONObject jsonObject = (JSONObject) obj;
//			JSONArray test = (JSONArray) jsonObject.get("Tests");
//
//			for (Object o : test) {
//				Object o1 = ((JSONObject) o).get("name");
//				data.put(((JSONObject) o1).get("name"), ((JSONObject) o).get("result"));
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return data;

	}
//
//	public static void main(String[] args) throws Exception {
//		JsonCircleCi sj = new JsonCircleCi();
//		Map<Object, Object> sample = sj.getTestMetaData();
//		for (Map.Entry<Object, Object> e : sample.entrySet()) {
//			String str1 = (String) e.getKey();
//			String str2 = (String) e.getValue();
//			System.out.println(str1 + "    " + str2);
//		}
//	}
}
