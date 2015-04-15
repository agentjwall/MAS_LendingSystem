package MAS_LendingSystem;
import repast.simphony.parameter.*;
public class RoundDownConverter implements StringConverter<Object> {

	
	public String toString(Object obj) {
		return obj.toString();
	}

	
	public Object fromString(String strRep) {
		int inputVal = (Integer) new StringConverterFactory.IntConverter().fromString(strRep);
		if (inputVal % 2 != 0) {
			inputVal--;
		}
		return inputVal;
	}
	

}
