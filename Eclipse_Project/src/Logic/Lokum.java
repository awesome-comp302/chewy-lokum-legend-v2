package Logic;

import java.util.Arrays;

public class Lokum extends ChewyObject implements Matchable{

	private boolean timed;

	public static final String specialTypes[] = { "Color Bomb",
			"Horizontal Striped", "Regular", "Vertical Striped", "Wrapped"

	};

	public static final String[] possibleTypes = {

	"brown hazelnut", "green pistachio", "red rose", "white coconut" };

	private String specialType;

	
	public Lokum clone() {
		return new Lokum(type, specialType, timed);
	}

	public Lokum(String type, String specialType, boolean timed) {
		this.specialType = specialType;
		this.timed = timed;
		if (isValid(type)) {
			this.type = type;
			
		} else {
			throw new IllegalArgumentException("Type: " + type + " is unknown.");
		}
	}
	
	public boolean isTimed() {
		return timed;
	}
	
	public void setTimed(boolean b){
		timed = b;
	}
	
	public Lokum(String type, String specialType)
			throws IllegalArgumentException {
		this.specialType = specialType;

		if (isValid(type)) {
			this.type = type;
		} else {
			throw new IllegalArgumentException("Type: " + type + " is unknown.");
		}
	}

	public Lokum(String type) {
		this(type, "Regular");
	}

	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}

	public String getSpecialType() {
		return specialType;
	}

	protected boolean isValid(String type) {
		return Arrays.binarySearch(possibleTypes, type) >= 0;
	}

	@Override
	public boolean isMatched(Matchable l) {

		if (l instanceof Lokum) {
			if (type.equalsIgnoreCase(((Lokum) l).getType())) {
				return true;
			}
		}
		return false;
	}

	public boolean isSpecial() {
		return !specialType.equals("Regular");
	}
}
