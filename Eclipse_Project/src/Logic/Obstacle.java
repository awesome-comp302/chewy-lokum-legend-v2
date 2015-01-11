package Logic;
import java.util.Arrays;


public class Obstacle extends ChewyObject{
	private static final String types[] = {"Standard Obstackle"};
	public Obstacle(String type) throws IllegalArgumentException{
		if (isValid(type)) {
			this.type = type;
		}
		else {
			throw new IllegalArgumentException("Illegal obstacle type");
		}
	}
	private boolean isValid(String type) {
		return Arrays.binarySearch(types, type) >= 0;
	}
	

}
