package Logic;
import java.util.HashMap;


public class MatchingScaleInformer {

	private HashMap<Integer, Integer> matchingScaleMap;
	
	
	private static final int RIGHT_SCALE_KEY = 0;
	private static final int LEFT_SCALE_KEY = 1;
	private static final int UP_SCALE_KEY = 2;
	private static final int DOWN_SCALE_KEY = 3;
	
	
	
	public MatchingScaleInformer() {
		matchingScaleMap = new HashMap<Integer, Integer>();
		matchingScaleMap.put(RIGHT_SCALE_KEY, 0);
		matchingScaleMap.put(LEFT_SCALE_KEY, 0);
		matchingScaleMap.put(UP_SCALE_KEY, 0);
		matchingScaleMap.put(DOWN_SCALE_KEY, 0);
		
		
	}
	
	public int horizontalMatchTotalScale()
	{
		return matchingScaleMap.get(RIGHT_SCALE_KEY) + matchingScaleMap.get(LEFT_SCALE_KEY) + 1;
	}
	
	public int verticalMatchTotalScale()
	{
		return matchingScaleMap.get(DOWN_SCALE_KEY) + matchingScaleMap.get(UP_SCALE_KEY) + 1;
	}


	public void setUpScale(int up) {
		matchingScaleMap.put(UP_SCALE_KEY, up);
	}

	public int getUpScale() {
		return matchingScaleMap.get(UP_SCALE_KEY);
	}

	
	public void setDownScale(int down) {
		matchingScaleMap.put(DOWN_SCALE_KEY, down);
	}

	public int getDownScale() {
		return matchingScaleMap.get(DOWN_SCALE_KEY);
	}
	
	public void setRightScale(int right) {
		matchingScaleMap.put(RIGHT_SCALE_KEY, right);
		
	}

	public int getRightScale() {
		return matchingScaleMap.get(RIGHT_SCALE_KEY);
	}
	
	
	public void setLeftScale(int left) {

		matchingScaleMap.put(LEFT_SCALE_KEY, left);
	}
	
	public int getLeftScale() {
		return matchingScaleMap.get(LEFT_SCALE_KEY);
	}

	@Override
	public String toString() {
		String result = "";
		for (int key : matchingScaleMap.keySet()) {
			switch (key) {
			case UP_SCALE_KEY:
				result += "up: ";
				break;
			case DOWN_SCALE_KEY:
				result += "down: ";
				break;
			case RIGHT_SCALE_KEY:
				result += "right: ";
				break;
			case LEFT_SCALE_KEY:
				result += "left: ";
				break;

			default:
				break;
			}
			result += matchingScaleMap.get(key) + " ";
		}
		result += "hr sum: " + horizontalMatchTotalScale();
		result += " vr sum: " + verticalMatchTotalScale();
		return result;
	}
	
	
	
}
