package CircleCi.Result;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class main {
//
//	public static void main(String[] args) {
//		Gson gson= new Gson();
//		BufferedReader br= null;
//		try{
//			br= new BufferedReader(new FileReader("CircleCIResult.json"));
//			Result result= gson.fromJson(br, Result.class);
//			System.out.println(result.getTests());
//			
//			if(result != null){
//				for(Test t : result.getTests()){
//				System.out.println(t.getName()+"  "+t.getResult());
//					
//				}
//			}
//			
//		}catch(FileNotFoundException e){
//			e.printStackTrace();
//		}finally{
//			try{
//			if(br != null){
//				br.close();
//			}}
//			catch(IOException e){
//				e.printStackTrace();
//			}
//		}
//		
//	}

}
