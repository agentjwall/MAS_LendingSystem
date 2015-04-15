package MAS_LendingSystem;
import repast.simphony.parameter.*;
public class RoundDownConverter implements StringConverter<Object> {

	@Override
	public String toString(Object obj) {
		return obj.toString();
	}

	@Override
	public Object fromString(String strRep) {
		int inputVal = (int) new StringConverterFactory.IntConverter().fromString(strRep);
		if (inputVal % 2 != 0) {
			inputVal--;
		}
		return inputVal;
	}
	

}
