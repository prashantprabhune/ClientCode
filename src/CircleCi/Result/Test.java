package CircleCi.Result;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

	@SerializedName("classname")
	@Expose
	private String classname;
	@SerializedName("file")
	@Expose
	private Object file;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("result")
	@Expose
	private String result;
	@SerializedName("run_time")
	@Expose
	private Double runTime;
	@SerializedName("message")
	@Expose
	private Object message;
	@SerializedName("source")
	@Expose
	private String source;
	@SerializedName("source_type")
	@Expose
	private String sourceType;

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public Object getFile() {
		return file;
	}

	public void setFile(Object file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Double getRunTime() {
		return runTime;
	}

	public void setRunTime(Double runTime) {
		this.runTime = runTime;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Override
	public String toString() {
		return "Test [classname=" + classname + ", file=" + file + ", name=" + name + ", result=" + result
				+ ", runTime=" + runTime + ", message=" + message + ", source=" + source + ", sourceType=" + sourceType
				+ ", getClassname()=" + getClassname() + ", getFile()=" + getFile() + ", getName()=" + getName()
				+ ", getResult()=" + getResult() + ", getRunTime()=" + getRunTime() + ", getMessage()=" + getMessage()
				+ ", getSource()=" + getSource() + ", getSourceType()=" + getSourceType() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
