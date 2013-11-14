package factories;

import java.util.ArrayList;
import java.util.List;

public class StudentChargesFactory {
	private List<String> errors = new ArrayList<String>();
	
	
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
}
