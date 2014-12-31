package Logic;

public class Move {
	private int type;
	private boolean special;
	public final int NORMAL = 0;
	public final int ONE_BOMB = 1;
	public final int ONE_WRAPPED = 2;
	public final int ONE_STRIPED = 3;
	public final int COMBO_BOMB = 4;
	public final int COMBO_WRAPPED = 5;
	public final int COMBO_STRIPED = 6;
	public final int BOMB_WRAPPED = 7;
	public final int BOMB_STRIPED = 8;
	public final int WRAPPED_STRIPED = 9;

	public Move(int x1, int y1, int x2, int y2, GamePlay gp, boolean special) {
		this.special = special;

		Lokum l1 = (Lokum) gp.getLevel().getBoard().cellAt(x1, y1)
				.getCurrentObject();
		Lokum l2 = (Lokum) gp.getLevel().getBoard().cellAt(x2, y2)
				.getCurrentObject();

		String type1 = l1.getSpecialType();
		String type2 = l2.getSpecialType();

		
		if (type1.equalsIgnoreCase(type2)) {// Combos and Regular
			if (type1.equals("Regular")) {
				type = NORMAL;
			} else if (type1.equals("Wrapped")) {
				type = COMBO_WRAPPED;
			} else if (type2.equalsIgnoreCase("Color Bomb")) {
				type = COMBO_BOMB;
			}
		} else if (type1.endsWith("Striped") && type2.endsWith("Striped")) { //Striped-Striped
			type = COMBO_STRIPED;
		} else if (type1.equals("Color Bomb") && type2.endsWith("Striped")
				|| type1.endsWith("Striped") && type2.equals("Color Bomb")) {//Color Bomb-Striped
			type = BOMB_STRIPED;
		} else if (type1.equals("Wrapped") && type2.endsWith("Striped")
				|| type1.endsWith("Striped") && type2.equals("Wrapped")) {//Striped-Wrapped
			type = WRAPPED_STRIPED;
		} else if (type1.equals("Wrapped") && type2.equals("Color Bomb")
				|| type1.equals("Color Bomb") && type2.equals("Wrapped")) {//Color Bomb - Wrapped
			type = BOMB_WRAPPED;
		}
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isSpecial() {
		return special;
	}
	
}
