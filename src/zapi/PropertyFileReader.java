package zapi;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

	private static String propertyFile = "resources\\testproperty\\input.properties";
	private static Properties prop;
	private static FileInputStream fis;

	public PropertyFileReader() throws IOException {
		prop = new Properties();
		fis = new FileInputStream(propertyFile);
		prop.load(fis);
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getZephyrBaseUrl() throws IOException {
		return prop.getProperty("ZephyrBaseUrl").trim().toString();
	}
		
	/**
	 * @return
	 * @throws IOException
	 */
	public String getAccessKey() throws IOException {
		return prop.getProperty("AccessKey").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getSecretKey() throws IOException {
		return prop.getProperty("SecretKey").trim().toString();
	}
	/**
	 * @return
	 * @throws IOException
	 */
	public String getUsername() throws IOException {
		return prop.getProperty("Username").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getProjectName() throws IOException {
		return prop.getProperty("ProjectName").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getFixVersionName() throws IOException {
		return prop.getProperty("FixVersionName").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getTestCycleName() throws IOException {
		return prop.getProperty("TestCycleName").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getTestCycleDescription() throws IOException {
		return prop.getProperty("TestCycleDescription").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getTestBuild() throws IOException {
		return prop.getProperty("TestBuild").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getTestEnvironment() throws IOException {
		return prop.getProperty("TestEnvironment").trim().toString();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public String getMethod() throws IOException {
		return prop.getProperty("Method").trim().toString();
	}
}

