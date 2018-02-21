package CircleCi.Result;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("tests")
@Expose
private List<Test> tests = null;
@SerializedName("exceptions")
@Expose
private List<Object> exceptions = null;

public List<Test> getTests() {
return tests;
}

public void setTests(List<Test> tests) {
this.tests = tests;
}

public List<Object> getExceptions() {
return exceptions;
}

public void setExceptions(List<Object> exceptions) {
this.exceptions = exceptions;
}

@Override
public String toString() {
	return "Result [tests=" + tests + ", exceptions=" + exceptions + ", getTests()=" + getTests() + ", getExceptions()="
			+ getExceptions() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
			+ super.toString() + "]";
}



}